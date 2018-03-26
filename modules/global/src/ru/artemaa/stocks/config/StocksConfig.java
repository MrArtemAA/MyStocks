package ru.artemaa.stocks.config;

import com.haulmont.cuba.core.config.Config;
import com.haulmont.cuba.core.config.Property;
import com.haulmont.cuba.core.config.Source;
import com.haulmont.cuba.core.config.SourceType;

/**
 * @author Artem Areshko
 * 20.03.2018
 */
@Source(type = SourceType.DATABASE)
public interface StocksConfig extends Config {
    @Property("stocks.user.email")
    String getUserEmail();

    @Property("stocks.currency.codes")
    String getCurrencyCodes();
}
