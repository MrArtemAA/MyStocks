create table STOCKS_TRANSFER (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    SOURCE_ACCOUNT_ID uuid,
    DEST_ACCOUNT_ID uuid,
    RATE decimal(19, 2),
    --
    primary key (ID)
);
