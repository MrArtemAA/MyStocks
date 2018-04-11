alter table STOCKS_ACCOUNT_OPERATION add column SOURCE_ACCOUNT_ID uuid ;
alter table STOCKS_ACCOUNT_OPERATION add column DEST_ACCOUNT_ID uuid ;
alter table STOCKS_ACCOUNT_OPERATION add column RATE decimal(19, 2) ;
