# 実装

## エンティティ

- エンティティには、`jakarta.persistence.Entity`の`@Entity`の付与が必須である。
- エンティティは、識別子を持つことが必須であり、識別子は値オブジェクトとして、`@EmbeddedId`の付与が必須である。
- エンティティは、識別子が同一であれば同一のエンティティとみなされる必要があるため、`equals`と
  `hashCode`のオーバーライドが必須である。
- エンティティは、`src/main/kotlin/dev/k_narusawa/ddd_demo/app/identity_access/domain/user/User.kt`
  を参考に作成すること。

## 値オブジェクト

- 値オブジェクトには、`jakarta.persistence.Embeddable`の`@Embeddable`の付与が必須である。
- data classを利用してクラスを作成する。
- 必要な条件があれば、initの中でrequireで値のチェックを行う。
- クラスのコンストラクタはprivateにして、プロパティは基本的には`value`
  のみとする。また、valueの可視性はprivateにし、専用のgetterを用意する。
