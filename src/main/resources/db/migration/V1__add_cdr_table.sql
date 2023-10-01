CREATE TABLE IF NOT EXISTS cdr.charging_data
(
    id                UUID  PRIMARY KEY,
    vehicle_id        VARCHAR(100) NOT NULL,
    session_id        UUID NOT NULL,
    start_time        TIMESTAMP NOT NULL,
    end_time          TIMESTAMP NOT NULL,
    amount            DOUBLE PRECISION,
    created_at        TIMESTAMP DEFAULT NOW(),
    updated_at        TIMESTAMP DEFAULT NOW()
);

CREATE OR REPLACE FUNCTION cdr.method_get_updated_at() RETURNS TRIGGER
    LANGUAGE plpgsql
AS '
    BEGIN
        NEW.updated_at =now();
        RETURN NEW;
    END;
';

CREATE TRIGGER trigger_updated_at BEFORE UPDATE ON cdr.charging_data
    FOR EACH ROW EXECUTE PROCEDURE cdr.method_get_updated_at();