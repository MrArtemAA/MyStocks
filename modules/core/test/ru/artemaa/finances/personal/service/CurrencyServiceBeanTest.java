package ru.artemaa.finances.personal.service;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import ru.artemaa.finances.personal.config.StocksConfig;
import ru.artemaa.finances.personal.entity.Currency;
import ru.artemaa.finances.personal.StocksTestContainer;

import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.math.BigDecimal.valueOf;
import static org.hamcrest.number.BigDecimalCloseTo.closeTo;
import static org.junit.Assert.assertThat;

public class CurrencyServiceBeanTest {
    @ClassRule
    public static StocksTestContainer container = StocksTestContainer.Common.INSTANCE;

    private static CurrencyServiceBean SERVICE;
    private static Currency USD;
    private static Currency RUB;
    private static Currency EUR;

    private static StocksConfig stocksConfig;

    @BeforeClass
    public static void setUpTest() {
        SERVICE = new CurrencyServiceBean(stocksConfig, container.metadata());
        USD = SERVICE.get("USD");
        RUB = SERVICE.get("RUB");
        EUR = SERVICE.get("EUR");
    }

    @Test
    public void testConvert() {
        BigDecimal bigDecimalOf100 = valueOf(100);

        BigDecimal convertUsdToRub = SERVICE.convert(USD, RUB, bigDecimalOf100);
        BigDecimal convertRubToUsd = SERVICE.convert(RUB, USD, convertUsdToRub);

        System.out.println(convertRubToUsd);
        assertThat(convertRubToUsd, closeTo(bigDecimalOf100, valueOf(0.00001)));
    }

    @Test
    public void testConvertChain() {
        BigDecimal bigDecimalOf100 = valueOf(100);

        BigDecimal convertUsdToEur = SERVICE.convert(USD, EUR, bigDecimalOf100);

        BigDecimal convertUsdToRub = SERVICE.convert(USD, RUB, bigDecimalOf100);
        BigDecimal convertRubToEur = SERVICE.convert(RUB, EUR, convertUsdToRub);

        System.out.println(convertUsdToEur);
        System.out.println(convertRubToEur);
        assertThat(convertUsdToEur, closeTo(convertRubToEur, valueOf(0.00001)));
    }

    @Test
    public void testConvertConcurrent() {
        CurrencyServiceBean currencyServiceBean = new CurrencyServiceBean(stocksConfig, container.metadata());
        Random random = new Random(31);
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        CountDownLatch countDownLatch = new CountDownLatch(100);
        for (int i = 0; i < 100; i++) {
            executorService.execute(() -> {
                currencyServiceBean.convert(USD, RUB, valueOf(random.nextInt(200)));
                countDownLatch.countDown();
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}