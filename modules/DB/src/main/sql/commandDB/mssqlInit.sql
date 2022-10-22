-- Create Schemas
CREATE SCHEMA app
    create Table app.Users (
        UID int UNIQUE,
        datetime datetime NOT NULL,
        eventType int NOT NULL,

        login varchar(50) UNIQUE NOT NULL,
        password varchar(50) NOT NULL,
        email varchar(50) UNIQUE NOT NULL
    )

    create Table app.Events (
        UID int NOT NULL,
        datetime datetime NOT NULL,
        eventType int NOT NULL,
        eventData varchar(1000) NOT NULL,

        FOREIGN KEY (UID) REFERENCES app.Users (UID),
        CHECK (eventType >= 0 and eventType <= 6)
    )
Go

CREATE SCHEMA sync
    create Table sync.TransactionOutbox (
        UID int NOT NULL,
        datetime datetime NOT NULL,
        eventType int NOT NULL,
        eventData varchar(1000) NOT NULL,
        completed bit NOT NULL DEFAULT 0,

        UNIQUE (UID, datetime, eventType)
    )
Go

CREATE SEQUENCE app.user_cnt
    START WITH 1
    INCREMENT BY 1 ;
