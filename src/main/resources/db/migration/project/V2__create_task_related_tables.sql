CREATE TABLE ddd_member (
    member_id VARCHAR(36) PRIMARY KEY,
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
