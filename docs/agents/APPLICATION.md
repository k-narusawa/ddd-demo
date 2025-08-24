# アプリケーション層ガイド

アプリケーション層は、ドメイン層のオブジェクト（エンティティやドメインサービス）を操作して、具体的なユースケースを実現する責務を持ちます。UIやフレームワークなどの外部要因からドメインロジックを隔離し、アプリケーションのビジネスロジックを記述する層です。

## 1. 構成要素

アプリケーション層は、主に以下の要素で構成されます。

- **ポート (Port)**: ユースケースへの入力点を定義するインターフェース。
- **ユースケース (Usecase / Interactor)**: ポートを実装し、具体的なビジネスロジックを実行するクラス。
- **DTO (Data Transfer Object)**: レイヤー間でデータを転送するためのオブジェクト。

```
src/main/kotlin/dev/knarusawa/ddd_demo/app/{context}/
└── application/
    ├── port/
    │   └── {UsecaseName}InputBoundary.kt  # ポート (インターフェース)
    └── usecase/
        └── {usecase_name}/
            ├── {UsecaseName}Interactor.kt   # ユースケース実装
            ├── {UsecaseName}InputData.kt    # 入力DTO
            └── {UsecaseName}OutputData.kt   # 出力DTO
```

## 2. ポート (Port / InputBoundary)

- **責務**:
    - アプリケーションが外部（コントローラーなど）に提供する機能を定義するインターフェースです。クリーンアーキテクチャにおける「入力ポート」に相当します。
    - これにより、ユースケースの実装詳細を隠蔽し、アダプター層との依存関係を逆転させます。
- **命名規則**: `{UsecaseName}InputBoundary`
- **実装例**: `dev.knarusawa.dddDemo.app.identityAccess.application.port.SignupUserInputBoundary`

## 3. ユースケース (Usecase / Interactor)

- **責務**:
    - `InputBoundary` インターフェースを実装し、具体的なユースケースのロジックを実行します。
    - `InputData` を受け取り、ドメインオブジェクトのメソッドを呼び出したり、リポジトリを使ってドメインオブジェクトを永続化したりします。
    - 処理結果を `OutputData` に詰めて返却します。
- **命名規則**: `{UsecaseName}Interactor`
- **実装例**:
  `dev.knarusawa.dddDemo.app.identityAccess.application.usecase.signup.SignupUserInteractor`

## 4. DTO (Data Transfer Object)

- **責務**:
    - アダプター層とアプリケーション層の間でデータをやり取りするためのオブジェクトです。
    - ドメインオブジェクトを直接アダプター層に公開することを防ぎます。
- **種類**:
    - **`InputData`**: ユースケースの入力として必要なデータを格納します。
    - **`OutputData`**: ユースケースの実行結果を格納します。
- **実装**:
    - `data class` を利用してシンプルに定義します。
    - バリデーションロジックなどは含めず、純粋なデータコンテナとして扱います。
- **実装例**:
    - `dev.knarusawa.dddDemo.app.identityAccess.application.usecase.signup.SignupUserInputData`
    - `dev.knarusawa.dddDemo.app.identityAccess.application.usecase.signup.SignupUserOutputData`

## 5. 例外処理

- ユースケースの実行中に発生したビジネスルール上のエラー（例: ユーザー名が既に存在する）は、アプリケーション層固有の例外として定義します。
- これにより、ドメイン層の例外とアプリケーション層の例外を区別し、アダプター層で適切なエラーハンドリングを行うことができます。
- **実装例**:
  `dev.knarusawa.dddDemo.app.identityAccess.application.exception.UsernameAlreadyExists`
