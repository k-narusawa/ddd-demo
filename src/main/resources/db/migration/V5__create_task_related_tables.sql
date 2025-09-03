CREATE TABLE ddd_member (
    member_id VARCHAR(36) PRIMARY KEY,
    personal_name VARCHAR(255) NOT NULL,
    version BIGINT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    created_by TEXT NOT NULL DEFAULT CURRENT_USER,
    updated_by TEXT NOT NULL DEFAULT CURRENT_USER
);

CREATE TRIGGER trigger_set_audit_columns
    BEFORE INSERT OR UPDATE ON ddd_member
    FOR EACH ROW
    EXECUTE FUNCTION set_audit_columns();

CREATE TABLE ddd_project (
    project_id VARCHAR(36) PRIMARY KEY,
    project_name VARCHAR(255) NOT NULL,
    version BIGINT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    created_by TEXT NOT NULL DEFAULT CURRENT_USER,
    updated_by TEXT NOT NULL DEFAULT CURRENT_USER
);
CREATE TRIGGER trigger_set_audit_columns
    BEFORE INSERT OR UPDATE ON ddd_project
    FOR EACH ROW
    EXECUTE FUNCTION set_audit_columns();

CREATE TABLE ddd_task_event (
    task_event_id VARCHAR(36) PRIMARY KEY,
    task_id VARCHAR(36),
    type VARCHAR(255) NOT NULL,
    project_id VARCHAR(36) NOT NULL,
    operator VARCHAR(36) NOT NULL,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    assigner VARCHAR(36),
    assignee VARCHAR(36),
    from_time TIMESTAMP,
    to_time TIMESTAMP,
    occurred_at TIMESTAMP NOT NULL,
    completed BOOLEAN NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    created_by TEXT NOT NULL DEFAULT CURRENT_USER,
    updated_by TEXT NOT NULL DEFAULT CURRENT_USER
);

CREATE INDEX idx_event_task_id ON ddd_task_event (task_id);

CREATE TRIGGER trigger_set_audit_columns
    BEFORE INSERT OR UPDATE ON ddd_task_event
    FOR EACH ROW
    EXECUTE FUNCTION set_audit_columns();
