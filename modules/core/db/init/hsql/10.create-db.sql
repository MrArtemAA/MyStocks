-- begin STOCKS_OPERATION
create table STOCKS_OPERATION (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    PORTFOLIO_ID varchar(36),
    STOCK_ID varchar(36),
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
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(80) not null,
    INDUSTRY_ID varchar(36),
    CODE varchar(20) not null,
    CURRENCY_CODE varchar(255),
    --
    primary key (ID)
)^
-- end STOCKS_STOCK
-- begin STOCKS_INVEST_IDEA
create table STOCKS_INVEST_IDEA (
    ID varchar(36) not null,
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
    DESCRIPTION longvarchar,
    ACTIVE boolean,
    --
    primary key (ID)
)^
-- end STOCKS_INVEST_IDEA
-- begin STOCKS_PAYMENT_CATEGORY
create table STOCKS_PAYMENT_CATEGORY (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    TYPE_ varchar(50),
    NAME varchar(255) not null,
    ACTIVE boolean,
    --
    primary key (ID)
)^
-- end STOCKS_PAYMENT_CATEGORY
-- begin STOCKS_PORTFOLIO
create table STOCKS_PORTFOLIO (
    ID varchar(36) not null,
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
-- begin STOCKS_INDUSTRY
create table STOCKS_INDUSTRY (
    ID varchar(36) not null,
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
-- begin STOCKS_ACCOUNT_OPERATION
create table STOCKS_ACCOUNT_OPERATION (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    DTYPE varchar(31),
    --
    TYPE_ varchar(50) not null,
    DATE_ date not null,
    SUM_ decimal(19, 2),
    COMMENT_ longvarchar,
    --
    -- from stocks$SimpleAccountOperation
    ACCOUNT_ID varchar(36),
    CATEGORY_ID varchar(36),
    --
    -- from stocks$Transfer
    SOURCE_ACCOUNT_ID varchar(36),
    DEST_ACCOUNT_ID varchar(36),
    RATE decimal(19, 2),
    --
    primary key (ID)
)^
-- end STOCKS_ACCOUNT_OPERATION
-- begin STOCKS_ACCOUNT_TYPE
create table STOCKS_ACCOUNT_TYPE (
    ID varchar(36) not null,
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
-- end STOCKS_ACCOUNT_TYPE
-- begin STOCKS_ACCOUNT
create table STOCKS_ACCOUNT (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255) not null,
    ACCOUNT_TYPE_ID varchar(36),
    CURRENCY_CODE varchar(255) not null,
    AMOUNT decimal(19, 2),
    CONSIDER_IN_BALANCE boolean,
    COMMENT_ longvarchar,
    --
    primary key (ID)
)^
-- end STOCKS_ACCOUNT
