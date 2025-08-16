# DBマイグレーションガイド

このプロジェクトでは、データベースのスキーマ管理に [Flyway](https://flywaydb.org/) を使用しています。
Flywayは、SQLベースのマイグレーションをバージョン管理し、データベーススキーマを段階的かつ確実に進化させることができます。

## 1. マイグレーションファイルの作成場所

マイグレーション用のSQLファイルは、以下のディレクトリに配置します。

```
src/main/resources/db/migration/
```

## 2. 命名規則

Flywayの規約に従い、マイグレーションファイルは以下の命名規則で作成する必要があります。

`V<バージョン>__<説明>.sql`

- **`V`**: バージョン管理されるマイグレーション（Versioned Migration）であることを示す接頭辞です。
- **`<バージョン>`**:
    - マイグレーションのバージョン番号です。
    - `1`, `2`, `3.1` のように、ドット (`.`) やアンダースコア (`_`) で区切られた数字で構成されます。
    - Flywayは、このバージョン番号の昇順でマイグレーションを実行します。
    - **既存のバージョン番号と重複しないように、最新のバージョン番号+1などを採番してください。**
- **`__` (ダブルアンダースコア)**: バージョンと説明を区切るセパレーターです。
- **`<説明>`**:
    - マイグレーションの内容を簡潔に説明する文字列です。
    - アンダースコア (`_`) を使って単語を区切ります。（例: `create_users_table`）
- **`.sql`**: SQLファイルの拡張子です。

### 具体例

- `V1__create_users_table.sql`
- `V2__add_email_to_users.sql`
- `V3_1__create_products_table.sql`

## 3. マイグレーションファイルの記述

- ファイルの中身は、標準的なDDL（Data Definition Language）文（`CREATE TABLE`, `ALTER TABLE`
  など）やDML（Data Manipulation Language）文（`INSERT`, `UPDATE` など）を記述します。
- 1つのファイルには、関連する一連の変更をまとめて記述することを推奨します。
- **一度適用されたマイグレーションファイルは、原則として変更しないでください。**
    - スキーマを変更したい場合は、新しいバージョンのマイグレーションファイルを作成して対応します。
- 全てのテーブル定義には以下の監査用の以下のカラムを追加してください。
    - created_at
    - updated_at
    - created_by
    - updated_by

    ```sql
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    created_by TEXT NOT NULL DEFAULT CURRENT_USER,
    updated_by TEXT NOT NULL DEFAULT CURRENT_USER,
    ```
- また全てのテーブルに以下の関数を設定してください。
  ```sql
  CREATE TRIGGER trigger_set_audit_columns
  BEFORE INSERT OR UPDATE ON ddd_actor_role
  FOR EACH ROW
  EXECUTE FUNCTION set_audit_columns();
  ```

## 4. マイグレーションの実行

マイグレーションは、Spring Bootアプリケーションの起動時に自動的に実行されます。
Flywayが現在のデータベースのスキーマバージョンを検出し、まだ適用されていない新しいバージョンのマイグレーションファイルを順番に適用します。
