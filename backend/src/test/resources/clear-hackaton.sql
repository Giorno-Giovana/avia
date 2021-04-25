DROP SCHEMA IF EXISTS backend CASCADE;
DO
$do$
    BEGIN
        IF NOT EXISTS(SELECT FROM pg_catalog.pg_roles WHERE rolname = 'backend')
            THEN CREATE USER backend PASSWORD 'postgres-password';
        END IF;
        CREATE SCHEMA IF NOT EXISTS AUTHORIZATION backend;
    END
$do$;

/*
    CREATE DATABASE hackathon;
    DO
    $do$
        BEGIN
            IF NOT EXISTS(SELECT FROM hackathon.pg_catalog.pg_roles WHERE rolname = 'hackathon') THEN
                CREATE USER hackathon PASSWORD 'postgres-password';
            END IF;
            GRANT ALL PRIVILEGES ON DATABASE hackathon TO hackathon;
            ALTER USER hackathon WITH SUPERUSER;
        END
    $do$;
*/
