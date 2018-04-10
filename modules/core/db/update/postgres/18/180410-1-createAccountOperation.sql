create table STOCKS_ACCOUNT_OPERATION (
    ID uuid,
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
    COMMENT_ text,
    --
    -- from stocks$SimpleAccountOperation
    ACCOUNT_ID uuid,
    CATEGORY_ID uuid,
    --
    primary key (ID)
);
