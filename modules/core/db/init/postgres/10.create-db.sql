-- begin STOCKS_OPERATION
create table STOCKS_OPERATION (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    PORTFOLIO_ID uuid,
    STOCK_ID uuid,
    TYPE_ integer not null,
    DATE_ date not null,
    PRICE double precision not null,
    AMOUNT integer not null,
    --
    primary key (ID)
)^
-- end STOCKS_OPERATION
-- begin STOCKS_STOCK
create table STOCKS_STOCK (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(80) not null,
    INDUSTRY_ID uuid,
    CODE varchar(20) not null,
    CURRENCY_CODE varchar(255),
    --
    primary key (ID)
)^
-- end STOCKS_STOCK
-- begin STOCKS_PORTFOLIO
create table STOCKS_PORTFOLIO (
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
    --
    primary key (ID)
)^
-- end STOCKS_PORTFOLIO
-- begin STOCKS_PORTFOLIO_INCOME
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
)^
-- end STOCKS_PORTFOLIO_INCOME
-- begin STOCKS_INDUSTRY
create table STOCKS_INDUSTRY (
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
    --
    primary key (ID)
)^
-- end STOCKS_INDUSTRY
-- begin STOCKS_INVEST_IDEA
create table STOCKS_INVEST_IDEA (
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
    END_DATE date,
    DESCRIPTION text,
    ACTIVE boolean,
    --
    primary key (ID)
)^
-- end STOCKS_INVEST_IDEA
