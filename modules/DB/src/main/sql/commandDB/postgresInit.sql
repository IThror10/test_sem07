CREATE SCHEMA if NOT EXISTS app;
CREATE SCHEMA if NOT EXISTS sync;

create Table if not exists app.Users (
    UID serial UNIQUE,
    datetime timestamp NOT NULL,
    eventType int NOT NULL,
    login varchar(100) UNIQUE NOT NULL,
    password varchar(100) NOT NULL,
    email varchar(100) UNIQUE NOT NULL);

create Table if not exists app.Events (
    UID int NOT NULL,
    datetime timestamp NOT NULL,
    eventType int NOT NULL,
    eventData jsonb NOT NULL,
    UNIQUE (UID, datetime, eventType),
    FOREIGN KEY (UID) REFERENCES app.Users (UID));

create Table if not exists sync.TransactionOutbox (
    UID int NOT NULL,
    datetime timestamp NOT NULL,
    eventType int NOT NULL,
    eventData jsonb NOT NULL,
    completed boolean NOT NULL DEFAULT false,
    UNIQUE (UID, datetime, eventType));


-- Create Functions
Create or Replace Function app.sync_events()
Returns Trigger AS $Body$ Begin
    Insert Into sync.TransactionOutbox values (
        new.UID,
        new.datetime,
        new.eventType,
        new.eventData
    );
    return new;
End $Body$ language plpgsql;

-- Add Triggers
Create or Replace Trigger event_transaction_sync
    After Insert on app.Events for each row
        execute Function app.sync_events();


Create or Replace Function app.sync_users()
Returns Trigger AS $Body$ Begin
    Insert Into sync.TransactionOutbox values (
        new.uid,
        new.datetime,
        new.eventType,
        '{}'
    );
    return new;
End $Body$ language plpgsql;

Create or Replace Trigger user_transaction_sync
    After Insert on app.Users for each row
        execute Function app.sync_users();