alter table stocks_currency rename to stocks_currency__UNUSED ;
alter table stocks_stock drop constraint FK_STOCKS_STOCK_CURRENCY ;
