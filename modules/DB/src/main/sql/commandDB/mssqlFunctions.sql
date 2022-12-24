-- Create Triggers

Create Trigger app.event_transaction_sync
ON app.Events AFTER INSERT AS
    INSERT INTO sync.TransactionOutbox (uid, datetime, eventType, eventData)
        SELECT uid, datetime, eventtype, eventdata FROM INSERTED;
Go

Create Trigger app.sync_users
ON app.Users AFTER INSERT AS
    INSERT INTO sync.TransactionOutbox (uid, datetime, eventType, eventData)
        SELECT uid, datetime, eventtype, '{}' FROM INSERTED;
Go

-- ReqAuthHandler.register()
Create Function app.authorize_user(@in_data varchar(1000))
    Returns @userInfo Table (
		uid int,
		login varchar(50)
	)
BEGIN
	IF NULL IN (SELECT uid FROM app.Users WHERE login = JSON_VALUE(@in_data, '$.Login') and password = JSON_VALUE(@in_data, '$.Password'))
		INSERT INTO @userInfo SELECT -1, null;
	ELSE
		INSERT INTO @userInfo SELECT uid, login FROM app.users WHERE
			login = JSON_VALUE(@in_data, '$.Login') and password = JSON_VALUE(@in_data, '$.Password')
	RETURN;
END
Go

--    -- ISynchronizer.synchronize()
--Create or Replace Function sync.get_records()
--    Returns setof sync.transactionOutbox
--AS $BODY$ Begin
--    return query
--        SELECT *
--            FROM sync.TransactionOutbox
--                ORDER BY datetime, UID, eventType;
--END $BODY$ language plpgsql;
