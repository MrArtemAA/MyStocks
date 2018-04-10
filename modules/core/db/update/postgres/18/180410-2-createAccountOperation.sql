alter table STOCKS_ACCOUNT_OPERATION add constraint FK_STOCKS_ACCOUNT_OPERATION_ACCOUNT foreign key (ACCOUNT_ID) references STOCKS_ACCOUNT(ID);
alter table STOCKS_ACCOUNT_OPERATION add constraint FK_STOCKS_ACCOUNT_OPERATION_CATEGORY foreign key (CATEGORY_ID) references STOCKS_PAYMENT_CATEGORY(ID);
create index IDX_STOCKS_ACCOUNT_OPERATION_ACCOUNT on STOCKS_ACCOUNT_OPERATION (ACCOUNT_ID);
create index IDX_STOCKS_ACCOUNT_OPERATION_CATEGORY on STOCKS_ACCOUNT_OPERATION (CATEGORY_ID);
