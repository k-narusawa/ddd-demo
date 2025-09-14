CREATE TABLE ddd_task_outbox(
    event_id VARCHAR(36) PRIMARY KEY,
    event_type VARCHAR(255) NOT NULL,
    payload JSONB NOT NULL,
    occurred_at TIMESTAMP NOT NULL,
    processed_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    created_by TEXT NOT NULL DEFAULT CURRENT_USER,
    updated_by TEXT NOT NULL DEFAULT CURRENT_USER
);


CREATE TRIGGER trigger_set_audit_columns
    BEFORE INSERT OR UPDATE ON ddd_task_outbox
    FOR EACH ROW
    EXECUTE FUNCTION set_audit_columns();

CREATE TRIGGER outbox_insert_trigger
  AFTER INSERT ON ddd_task_outbox
  FOR EACH ROW
  EXECUTE FUNCTION notify_outbox_event();
