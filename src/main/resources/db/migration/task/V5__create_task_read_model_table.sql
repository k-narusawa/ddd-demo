CREATE TABLE ddd_task_read_model (
    task_id VARCHAR(36) PRIMARY KEY,
    project_id VARCHAR(36) NOT NULL,
    operator VARCHAR(36) NOT NULL,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    assigner VARCHAR(36),
    assignee VARCHAR(36),
    from_time TIMESTAMP,
    to_time TIMESTAMP,
    completed BOOLEAN NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    created_by TEXT NOT NULL DEFAULT CURRENT_USER,
    updated_by TEXT NOT NULL DEFAULT CURRENT_USER
);

CREATE TRIGGER trigger_set_audit_columns
    BEFORE INSERT OR UPDATE ON ddd_task_read_model
    FOR EACH ROW
    EXECUTE FUNCTION set_audit_columns();
