package ru.artemaa.stocks.service;

import com.haulmont.cuba.core.global.DataManager;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service(InvestIdeaNotificationService.NAME)
public class InvestIdeaNotificationServiceBean implements InvestIdeaNotificationService {
    @Inject
    private DataManager dataManager;

    @Override
    public void checkInvestIdeas() {

    }
}