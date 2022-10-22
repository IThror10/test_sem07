-- Create Roles
Create User query_test_reader Password 'read_only';
Create User query_test_synchronizer Password 'synch';

-- Grant Privileges
    GRANT USAGE ON SCHEMA data TO query_test_synchronizer;
GRANT EXECUTE ON ALL PROCEDURES IN SCHEMA data TO query_test_synchronizer;
GRANT SELECT ON ALL TABLES IN SCHEMA data TO query_test_synchronizer;
GRANT SELECT ON SEQUENCE data.lit_lid_seq TO query_test_synchronizer;

    GRANT USAGE ON SCHEMA data TO query_test_reader;
GRANT SELECT ON ALL TABLES IN SCHEMA data TO query_test_reader;
GRANT SELECT ON SEQUENCE data.lit_lid_seq TO query_test_reader;