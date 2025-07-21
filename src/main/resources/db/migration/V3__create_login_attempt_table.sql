CREATE TABLE ddd_login_attempt (
  user_id VARCHAR(36) PRIMARY KEY,
  failure_count Int NOT NULL,
  last_attempt_timestamp TIMESTAMP,
  lock_expiration_timestamp TIMESTAMP,
  version  BIGINT NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT NOW(),
  updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
  created_by TEXT NOT NULL DEFAULT CURRENT_USER,
  updated_by TEXT NOT NULL DEFAULT CURRENT_USER
);

CREATE TRIGGER trigger_set_audit_columns
    BEFORE INSERT OR UPDATE ON ddd_login_attempt
    FOR EACH ROW
    EXECUTE FUNCTION set_audit_columns();

