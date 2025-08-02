CREATE TABLE ddd_token
(
    token_id                 VARCHAR(36) PRIMARY KEY,
    user_id                  VARCHAR(36)        NOT NULL,
    access_token             TEXT        NOT NULL,
    refresh_token            TEXT        NOT NULL,
    access_token_expiration  TIMESTAMP   NOT NULL,
    refresh_token_expiration TIMESTAMP   NOT NULL,
    is_revoked               BOOLEAN     NOT NULL,
    version                  BIGINT      NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    created_by TEXT NOT NULL DEFAULT CURRENT_USER,
    updated_by TEXT NOT NULL DEFAULT CURRENT_USER,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES ddd_user (user_id)
);

CREATE INDEX idx_token_user_id ON ddd_token (user_id);

CREATE TRIGGER trigger_set_audit_columns
  BEFORE INSERT OR UPDATE ON ddd_token
  FOR EACH ROW
  EXECUTE FUNCTION set_audit_columns();
