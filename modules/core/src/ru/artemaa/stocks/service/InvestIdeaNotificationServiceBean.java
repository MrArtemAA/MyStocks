package ru.artemaa.stocks.service;

import com.haulmont.cuba.core.app.EmailerAPI;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.EmailInfo;
import com.haulmont.cuba.core.global.LoadContext;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import ru.artemaa.stocks.config.StocksConfig;
import ru.artemaa.stocks.entity.InvestIdea;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(InvestIdeaNotificationService.NAME)
public class InvestIdeaNotificationServiceBean implements InvestIdeaNotificationService {
    @Inject
    private DataManager dataManager;
    @Inject
    private EmailerAPI emailerAPI;
    @Inject
    private StocksConfig stocksConfig;

    @Override
    public void checkInvestIdeas() {
        List<InvestIdea> investIdeas = dataManager.loadList(LoadContext.create(InvestIdea.class).setQuery(
                LoadContext.createQuery("select e from stocks$InvestIdea e " +
                        "where e.active = TRUE and e.endDate <= :endDate")
                .setParameter("endDate", new Date())
        ));

        String userEmail = stocksConfig.getUserEmail();
        if (StringUtils.isEmpty(userEmail)) {
            throw new RuntimeException("В свойствах базы данных не указан email пользователя для отправки уведомления");
        }
        Map<String, Serializable> templateParams = new HashMap<>();
        templateParams.put("investIdeas", (Serializable) investIdeas);
        emailerAPI.sendEmailAsync(new EmailInfo(
                userEmail,
                "Доброе утро! Ваша информация по текущим инвестиционным идеям",
                null,
                "ru/artemaa/stocks/mailtemplates/invest-ideas-summary.html",
                templateParams
        ));
    }
}