CREATE TABLE ddd_actor_role (
    actor_role_id VARCHAR(36) PRIMARY KEY,
    actor_id VARCHAR(36) NOT NULL,
    team_id VARCHAR(36) NOT NULL,
    role VARCHAR(255) NOT NULL,
    version BIGINT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    created_by TEXT NOT NULL DEFAULT CURRENT_USER,
    updated_by TEXT NOT NULL DEFAULT CURRENT_USER,
    FOREIGN KEY (actor_id) REFERENCES ddd_actor(actor_id),
    FOREIGN KEY (team_id) REFERENCES ddd_team(team_id)
);

CREATE INDEX idx_actor_id_and_team_id ON ddd_actor_role (actor_id, team_id);

CREATE TRIGGER trigger_set_audit_columns
    BEFORE INSERT OR UPDATE ON ddd_actor_role
    FOR EACH ROW
    EXECUTE FUNCTION set_audit_columns();
