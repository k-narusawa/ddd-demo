CREATE OR REPLACE FUNCTION set_audit_columns() 
  RETURNS trigger AS $$
  BEGIN
    IF TG_OP = 'INSERT' THEN
        NEW.created_at := NOW();
        NEW.updated_at := NOW();
        NEW.created_by := CURRENT_USER;
        NEW.updated_by := CURRENT_USER;
    ELSIF TG_OP = 'UPDATE' THEN
        NEW.created_at := OLD.created_at;
        NEW.updated_at := NOW();
        NEW.created_by := OLD.created_by;
        NEW.updated_by := CURRENT_USER;
    END IF;

    RETURN NEW;
  END;
  $$ LANGUAGE plpgsql;
