CREATE TABLE ddd_user (
  user_id VARCHAR(36) PRIMARY KEY,
  username VARCHAR(255) NOT NULL,
  password TEXT NOT NULL,
  email_verified BOOLEAN NOT NULL,
  given_name VARCHAR(255) NOT NULL,
  family_name VARCHAR(255) NOT NULL,
  login_failure_count INTEGER,
  last_login_failed_at TIMESTAMP,
  account_status VARCHAR(255),
  version  BIGINT NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT NOW(),
  updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
  created_by TEXT NOT NULL DEFAULT CURRENT_USER,
  updated_by TEXT NOT NULL DEFAULT CURRENT_USER
);

CREATE TRIGGER trigger_set_audit_columns
    BEFORE INSERT OR UPDATE ON ddd_user
    FOR EACH ROW
    EXECUTE FUNCTION set_audit_columns();
