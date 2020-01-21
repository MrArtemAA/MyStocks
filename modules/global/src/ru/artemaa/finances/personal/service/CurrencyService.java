package ru.artemaa.finances.personal.service;


import ru.artemaa.finances.personal.entity.Currency;

import java.math.BigDecimal;
import java.util.Collection;

public interface CurrencyService {
    String NAME = "stocks_CurrencyService";
    Collection<Currency> getAll();
    Currency get(String code);
    BigDecimal convert(Currency fromCurrency, Currency toCurrency, BigDecimal amount);
}