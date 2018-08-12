alter table STOCKS_STOCK rename column currency_id__unused to currency_id__unused__u30798 ;
alter table STOCKS_STOCK alter column currency_id__unused__u30798 drop not null ;
