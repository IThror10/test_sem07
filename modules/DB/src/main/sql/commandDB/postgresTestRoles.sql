Create User command_test_access Password 'read_only';
Create User command_test_authorized Password 'write_only';
Create User command_test_synchronizer Password 'sync';

    GRANT USAGE ON SCHEMA app TO command_test_access;
GRANT EXECUTE ON FUNCTION app.register_user TO command_test_access;
GRANT EXECUTE ON FUNCTION app.authorize_user TO command_test_access;

    GRANT USAGE ON SCHEMA app TO command_test_authorized;
GRANT EXECUTE ON FUNCTION app.accept_event TO command_test_authorized;

    GRANT USAGE ON SCHEMA sync TO command_test_synchronizer;
GRANT SELECT, UPDATE, DELETE ON TABLE sync.TransactionOutbox TO command_test_synchronizer;