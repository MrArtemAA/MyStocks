-- begin STOCKS_OPERATION
alter table STOCKS_OPERATION add constraint FK_STOCKS_OPERATION_PORTFOLIO foreign key (PORTFOLIO_ID) references STOCKS_PORTFOLIO(ID)^
alter table STOCKS_OPERATION add constraint FK_STOCKS_OPERATION_STOCK foreign key (STOCK_ID) references STOCKS_STOCK(ID)^
create index IDX_STOCKS_OPERATION_PORTFOLIO on STOCKS_OPERATION (PORTFOLIO_ID)^
create index IDX_STOCKS_OPERATION_STOCK on STOCKS_OPERATION (STOCK_ID)^
-- end STOCKS_OPERATION
-- begin STOCKS_STOCK
alter table STOCKS_STOCK add constraint FK_STOCKS_STOCK_INDUSTRY foreign key (INDUSTRY_ID) references STOCKS_INDUSTRY(ID)^
create unique index IDX_STOCKS_STOCK_UK_NAME on STOCKS_STOCK (NAME) where DELETE_TS is null ^
create unique index IDX_STOCKS_STOCK_UK_CODE on STOCKS_STOCK (CODE) where DELETE_TS is null ^
create index IDX_STOCKS_STOCK_INDUSTRY on STOCKS_STOCK (INDUSTRY_ID)^
-- end STOCKS_STOCK
-- begin STOCKS_PORTFOLIO_INCOME
alter table STOCKS_PORTFOLIO_INCOME add constraint FK_STOCKS_PORTFOLIO_INCOME_PORTFOLIO foreign key (PORTFOLIO_ID) references STOCKS_PORTFOLIO(ID)^
create index IDX_STOCKS_PORTFOLIO_INCOME_PORTFOLIO on STOCKS_PORTFOLIO_INCOME (PORTFOLIO_ID)^
-- end STOCKS_PORTFOLIO_INCOME
-- begin STOCKS_ACCOUNT
alter table STOCKS_ACCOUNT add constraint FK_STOCKS_ACCOUNT_ACCOUNT_TYPE foreign key (ACCOUNT_TYPE_ID) references STOCKS_ACCOUNT_TYPE(ID)^
create index IDX_STOCKS_ACCOUNT_ACCOUNT_TYPE on STOCKS_ACCOUNT (ACCOUNT_TYPE_ID)^
-- end STOCKS_ACCOUNT
-- begin STOCKS_ACCOUNT_OPERATION
alter table STOCKS_ACCOUNT_OPERATION add constraint FK_STOCKS_ACCOUNT_OPERATION_ACCOUNT foreign key (ACCOUNT_ID) references STOCKS_ACCOUNT(ID)^
alter table STOCKS_ACCOUNT_OPERATION add constraint FK_STOCKS_ACCOUNT_OPERATION_CATEGORY foreign key (CATEGORY_ID) references STOCKS_PAYMENT_CATEGORY(ID)^
alter table STOCKS_ACCOUNT_OPERATION add constraint FK_STOCKS_ACCOUNT_OPERATION_SOURCE_ACCOUNT foreign key (SOURCE_ACCOUNT_ID) references STOCKS_ACCOUNT(ID)^
alter table STOCKS_ACCOUNT_OPERATION add constraint FK_STOCKS_ACCOUNT_OPERATION_DEST_ACCOUNT foreign key (DEST_ACCOUNT_ID) references STOCKS_ACCOUNT(ID)^
create index IDX_STOCKS_ACCOUNT_OPERATION_ACCOUNT on STOCKS_ACCOUNT_OPERATION (ACCOUNT_ID)^
create index IDX_STOCKS_ACCOUNT_OPERATION_CATEGORY on STOCKS_ACCOUNT_OPERATION (CATEGORY_ID)^
create index IDX_STOCKS_ACCOUNT_OPERATION_SOURCE_ACCOUNT on STOCKS_ACCOUNT_OPERATION (SOURCE_ACCOUNT_ID)^
create index IDX_STOCKS_ACCOUNT_OPERATION_DEST_ACCOUNT on STOCKS_ACCOUNT_OPERATION (DEST_ACCOUNT_ID)^
-- end STOCKS_ACCOUNT_OPERATION
