CREATE TABLE ddd_task_event (
    task_event_id VARCHAR(36) PRIMARY KEY,
    task_id VARCHAR(36) NOT NULL,
    task_action VARCHAR(255) NOT NULL,
    occurred_by VARCHAR(36) NOT NULL,
    occurred_on TIMESTAMP NOT NULL,
    version BIGINT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    created_by TEXT NOT NULL DEFAULT CURRENT_USER,
    updated_by TEXT NOT NULL DEFAULT CURRENT_USER,
    FOREIGN KEY (task_id) REFERENCES ddd_task(task_id)
);

CREATE TRIGGER trigger_set_audit_columns
    BEFORE INSERT OR UPDATE ON ddd_task_event
    FOR EACH ROW
    EXECUTE FUNCTION set_audit_columns();
