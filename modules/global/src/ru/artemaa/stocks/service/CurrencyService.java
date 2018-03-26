package ru.artemaa.stocks.service;


import ru.artemaa.stocks.entity.Currency;

import java.util.Collection;

public interface CurrencyService {
    String NAME = "stocks_CurrencyService";
    Collection<Currency> getAll();
    Currency get(String code);
}