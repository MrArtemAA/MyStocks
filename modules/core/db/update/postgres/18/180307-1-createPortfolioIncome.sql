create table STOCKS_PORTFOLIO_INCOME (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    DATE_ date not null,
    AMOUNT bigint not null,
    PORTFOLIO_ID uuid,
    --
    primary key (ID)
);
