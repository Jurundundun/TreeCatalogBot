package com.example.treecatalogbot.config;

import com.example.treecatalogbot.service.TelegramBot;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * Компонент инициализации бота, который регистрирует Telegram бота при запуске приложения.
 */
@Component
@RequiredArgsConstructor
public class BotInitializer {

    private final TelegramBot telegramBot;

    /**
     * Инициализирует регистрацию Telegram бота при запуске контекста приложения.
     *
     * @throws TelegramApiException в случае возникновения ошибки при регистрации бота.
     */
    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            telegramBotsApi.registerBot(telegramBot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
