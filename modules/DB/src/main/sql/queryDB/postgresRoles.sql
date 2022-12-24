-- Create Roles
Create User query_reader Password 'read_only';
Create User query_synchronizer Password 'synch';

-- Grant Privileges
    GRANT USAGE ON SCHEMA data TO query_synchronizer;
GRANT EXECUTE ON ALL PROCEDURES IN SCHEMA data TO query_synchronizer;
GRANT SELECT ON ALL TABLES IN SCHEMA data TO query_synchronizer;
GRANT SELECT ON SEQUENCE data.lit_lid_seq TO query_synchronizer;

    GRANT USAGE ON SCHEMA data TO query_reader;
GRANT SELECT ON ALL TABLES IN SCHEMA data TO query_reader;
GRANT SELECT ON SEQUENCE data.lit_lid_seq TO query_reader;
