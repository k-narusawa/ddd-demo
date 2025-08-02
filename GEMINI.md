# 実でDDDを参考に実装をしてみるプロジェクト

## 実装

### エンティティ

#### 概要

- エンティティには、`jakarta.persistence.Entity`の`@Entity`の付与が必須である。
- エンティティは、識別子を持つことが必須であり、識別子は値オブジェクトとして、`@EmbeddedId`の付与が必須である。
- エンティティは、識別子が同一であれば同一のエンティティとみなされる必要があるため、`equals`と
  `hashCode`のオーバーライドが必須である。
- エンティティは、`src/main/kotlin/dev/k_narusawa/ddd_demo/app/identity_access/domain/user/User.kt`
  を参考に作成すること。

### 値オブジェクト

- 値オブジェクトには、`jakarta.persistence.Embeddable`の`@Embeddable`の付与が必須である。
- data classを利用してクラスを作成する。
- 必要な条件があれば、initの中でrequireで値のチェックを行う。
- クラスのコンストラクタはprivateにして、プロパティは基本的には`value`
  のみとする。また、valueの可視性はprivateにし、専用のgetterを用意する。

## テスト

### 単体テスト

- テストはAAA(3A)パターンにしてください。AAAパターンとは以下の3つのパターンで構成されるテストケースになります。
    - 準備(Arrange)フェーズ
    - 実行(Act)フェーズ
    - 確認(Assert)フェーズ
- 単体テストにおいて同じフェーズを複数用意することはしないでください。 ※ 統合テストであれば問題ありません。
    - テストの実行時間が長くなるのを避けるためです。
- 単体テストにおいてif文の使用はしないでください。
- 実行フェーズのサイズは1行を超えないようにしてください。
- AAAのフェーズに後始末のフェーズ設けないでください。
- 各フェーズのコードの前にそのフェーズであることを示すコメントはしないでください。
    - 空行を挿入はしてください。
- テスト対象システムの変数名はsut(System Under Test)にしてください。

#### 集約・エンティティ

- 対象のエンティティの持つ振る舞いについてのテストを行う
- イベントの発行機能を行う場合にには、`@SpringBootTest`をクラスに付与して実施する

##### 実装例

```kotlin
@SpringBootTest
class UserTest {
  companion object {
    private const val DUMMY_UA = "dummy_agent"
    private const val DUMMY_IP = "127.0.0.1"
  }

  @BeforeEach
  fun setup() {
    mockkObject(
      ChangeUsernameEventPublisher.Companion,
    )
    every { ChangeUsernameEventPublisher.Companion.publish(any()) } returns Unit
  }

  @Nested
  inner class Identifier {
    @Test
    @DisplayName("同じUserIdを持つUserは等価である")
    fun `test equals and hashCode with same ID`() {
      val userId = UserId.new()
      val user1 = createUserInstance(
        userId,
        Username.of("Taro")
      )
      val user2 = createUserInstance(
        userId,
        Username.of("Jiro")
      )

      Assertions.assertEquals(user1, user2)
      Assertions.assertEquals(user1.hashCode(), user2.hashCode())
    }

    @Test
    @DisplayName("異なるUserIdを持つUserは等価ではない")
    fun `test equals with different IDs`() {
      val user1 = createUserInstance(
        UserId.new(),
        Username.of("Taro")
      )
      val user2 = createUserInstance(
        UserId.new(),
        Username.of("Taro")
      )

      Assertions.assertNotEquals(user1, user2)
    }
  }

  @Nested
  inner class Registration {
    @Test
    @DisplayName("ユーザー登録ができる")
    fun user_can_create_new_account() {
      val username = Username.of("Taro")
      val passwordString = "!Password0"
      val password = Password.of(passwordString)
      val user = User.register(username, password)

      Assertions.assertEquals(username, user.getUsername())
    }
  }

  @Nested
  inner class ChangeUsername {
    @Test
    @DisplayName("ユーザー名を変更できる")
    fun user_can_change_username() {
      val user = createUserInstance(
        UserId.new(),
        Username.of("Taro")
      )
      val newUsername = Username.of("Jiro")
      user.changeUsername(newUsername, DUMMY_UA, DUMMY_IP)

      Assertions.assertEquals(newUsername, user.getUsername())
    }

    @Test
    @DisplayName("ユーザー名を変更した場合にイベントが発行される")
    fun when_user_change_username_publish_event() {
      val user = createUserInstance(
        UserId.new(),
        Username.of("Taro")
      )
      val newUsername = Username.of("Jiro")

      user.changeUsername(newUsername, DUMMY_UA, DUMMY_IP)

      verify(exactly = 1) { ChangeUsernameEventPublisher.Companion.publish(any()) }
    }
  }

  @Nested
  inner class PasswordMatching {
    @Test
    @DisplayName("パスワードが一致する")
    fun user_can_verify_password() {
      val rawPassword = "!Password0"
      val password = Password.of(rawPassword)
      val user = User.register(Username.of("Taro"), password)
      createUserInstance()

      Assertions.assertDoesNotThrow {
        user.verifyPassword(rawPassword, DUMMY_UA, DUMMY_IP)
      }
    }

    @Test
    @DisplayName("パスワードが一致しない場合に例外が投げられる")
    fun when_user_mistake_password_throw_exception() {
      val rawPassword = "!Password0"
      val anotherRawPassword = "!Password1"
      val password = Password.of(rawPassword)
      val user = User.register(Username.of("Taro"), password)

      Assertions.assertThrows(AuthenticationException::class.java) {
        user.verifyPassword(anotherRawPassword, DUMMY_UA, DUMMY_IP)
      }
    }
  }

  private fun createUserInstance(
    userId: UserId = UserId.new(),
    username: Username = Username.of("dummy"),
    password: Password = Password.of("dummy")
  ): User {
    val constructor = User::class.primaryConstructor!!
    constructor.isAccessible = true
    return constructor.call(
      userId,
      username,
      password,
      1L,
    )
  }
}
```

#### 値オブジェクト

- コンストラクタに同じ値を設定して作成したインスタンスが同一のものであることを確認する。

##### 実装例

```kotlin
class UserIdTest {
  @Nested
  @DisplayName("インスタンス化")
  inner class Instantiation {
    @Test
    @DisplayName("コンストラクタでインスタンスを生成できる")
    fun `can instantiate with constructor`() {
      val uuid = UUID.randomUUID().toString()

      val sut = UserId(uuid)

      assertEquals(uuid, sut.get())
    }

    @Test
    @DisplayName("new メソッドでインスタンスを生成できる")
    fun `can instantiate with new method`() {
      val sut = UserId.new()

      assertNotNull(sut)
      assertDoesNotThrow { UUID.fromString(sut.get()) }
    }
  }


  @Nested
  @DisplayName("等価性")
  inner class Equality {
    @Test
    @DisplayName("同じ値を持つインスタンスは等価である")
    fun `instances with same value are equal`() {
      val uuid = UUID.randomUUID().toString()
      val id1 = UserId(uuid)
      val id2 = UserId(uuid)

      assertEquals(id1, id2)
      assertEquals(id1.hashCode(), id2.hashCode())
    }

    @Test
    @DisplayName("異なる値を持つインスタンスは等価ではない")
    fun `instances with different values are not equal`() {
      val id1 = UserId.new()
      val id2 = UserId.new()

      assertNotEquals(id1, id2)
    }
  }
}
```
