alter table STOCKS_TRANSFER add constraint FK_STOCKS_TRANSFER_SOURCE_ACCOUNT foreign key (SOURCE_ACCOUNT_ID) references STOCKS_ACCOUNT(ID);
alter table STOCKS_TRANSFER add constraint FK_STOCKS_TRANSFER_DEST_ACCOUNT foreign key (DEST_ACCOUNT_ID) references STOCKS_ACCOUNT(ID);
create index IDX_STOCKS_TRANSFER_SOURCE_ACCOUNT on STOCKS_TRANSFER (SOURCE_ACCOUNT_ID);
create index IDX_STOCKS_TRANSFER_DEST_ACCOUNT on STOCKS_TRANSFER (DEST_ACCOUNT_ID);