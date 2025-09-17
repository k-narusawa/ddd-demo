CREATE TABLE ddd_event (
    event_id VARCHAR(36) PRIMARY KEY,
    aggregate_id VARCHAR(36) NOT NULL,
    aggregate_type VARCHAR(255) NOT NULL,
    event_type VARCHAR(255) NOT NULL,
    event_data BYTEA NOT NULL,
    occurred_at TIMESTAMP NOT NULL,
    published_at TIMESTAMP,
    version  BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    created_by TEXT NOT NULL DEFAULT CURRENT_USER,
    updated_by TEXT NOT NULL DEFAULT CURRENT_USER
);

CREATE INDEX idx_event_aggregate_id ON ddd_event (aggregate_id);

CREATE TRIGGER trigger_set_audit_columns
    BEFORE INSERT OR UPDATE ON ddd_event
    FOR EACH ROW
    EXECUTE FUNCTION set_audit_columns();

CREATE TRIGGER event_insert_trigger
  AFTER INSERT ON ddd_event
  FOR EACH ROW
  EXECUTE FUNCTION notify_event();
