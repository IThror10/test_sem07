-- Create Roles
Create User command_access Password 'read_only';
Create User command_authorized Password 'write_only';
Create User command_synchronizer Password 'sync';

    GRANT USAGE ON SCHEMA app TO command_access;
GRANT EXECUTE ON FUNCTION app.register_user TO command_access;
GRANT EXECUTE ON FUNCTION app.authorize_user TO command_access;

    GRANT USAGE ON SCHEMA app TO command_authorized;
GRANT EXECUTE ON FUNCTION app.accept_event TO command_authorized;

    GRANT USAGE ON SCHEMA sync TO command_synchronizer;
GRANT SELECT, UPDATE, DELETE ON TABLE sync.TransactionOutbox TO command_synchronizer;