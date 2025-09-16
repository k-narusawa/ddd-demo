CREATE TABLE ddd_activity_log (
  activity_log_id VARCHAR(36) PRIMARY KEY,
  user_id VARCHAR(36) NOT NULL,
  action_type VARCHAR(50) NOT NULL,
  ip_address VARCHAR(45),
  user_agent TEXT,
  occurred_on TIMESTAMP NOT NULL,
  version  BIGINT NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT NOW(),
  updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
  created_by TEXT NOT NULL DEFAULT CURRENT_USER,
  updated_by TEXT NOT NULL DEFAULT CURRENT_USER
);

CREATE TRIGGER trigger_set_audit_columns
  BEFORE INSERT OR UPDATE ON ddd_activity_log
  FOR EACH ROW
  EXECUTE FUNCTION set_audit_columns();
