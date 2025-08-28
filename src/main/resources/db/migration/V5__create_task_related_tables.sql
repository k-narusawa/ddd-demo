CREATE TABLE ddd_actor (
    actor_id VARCHAR(36) PRIMARY KEY,
    personal_name VARCHAR(255) NOT NULL,
    version BIGINT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    created_by TEXT NOT NULL DEFAULT CURRENT_USER,
    updated_by TEXT NOT NULL DEFAULT CURRENT_USER
);

CREATE TRIGGER trigger_set_audit_columns
    BEFORE INSERT OR UPDATE ON ddd_actor
    FOR EACH ROW
    EXECUTE FUNCTION set_audit_columns();

CREATE TABLE ddd_team (
    team_id VARCHAR(36) PRIMARY KEY,
    team_name VARCHAR(255) NOT NULL,
    version BIGINT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    created_by TEXT NOT NULL DEFAULT CURRENT_USER,
    updated_by TEXT NOT NULL DEFAULT CURRENT_USER
);
CREATE TRIGGER trigger_set_audit_columns
    BEFORE INSERT OR UPDATE ON ddd_team
    FOR EACH ROW
    EXECUTE FUNCTION set_audit_columns();
