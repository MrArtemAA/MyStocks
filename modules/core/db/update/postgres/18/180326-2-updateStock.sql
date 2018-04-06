alter table STOCKS_STOCK rename column currency_id to currency_id__UNUSED ;
--alter table STOCKS_STOCK alter column currency_id__UNUSED drop not null ;
drop index IDX_STOCKS_STOCK_CURRENCY ;
alter table STOCKS_STOCK add column CURRENCY_CODE varchar(255) ;
