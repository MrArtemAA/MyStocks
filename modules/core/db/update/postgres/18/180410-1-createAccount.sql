create table STOCKS_ACCOUNT (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255) not null,
    ACCOUNT_TYPE_ID uuid,
    CURRENCY_CODE varchar(255) not null,
    AMOUNT decimal(19, 2),
    CONSIDER_IN_BALANCE boolean,
    COMMENT_ text,
    --
    primary key (ID)
);
