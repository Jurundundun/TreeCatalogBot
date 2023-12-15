package com.example.treecatalogbot.util;

import com.example.treecatalogbot.command.BotCommand;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Провайдер команд для работы с ботом Telegram.
 */
@Component
public class CommandProvider implements ApplicationContextAware {

    private static ApplicationContext context;

    /**
     * Получает все команды бота из контекста приложения.
     *
     * @return Map, содержащая команды бота, сопоставленные их идентификаторам.
     */
    public static Map<String, BotCommand> getAllBotCommands() {
        Map<String, BotCommand> commandMap = new HashMap<>();
        Map<String, BotCommand> commandBeans = context.getBeansOfType(BotCommand.class);

        // Проходит по всем найденным бинам BotCommand и добавляет их в карту команд
        for (BotCommand command : commandBeans.values()) {
            String identifier = command.getCommandIdentifier();
            commandMap.put(identifier, command);
        }

        return commandMap;
    }

    /**
     * Устанавливает контекст приложения для доступа к бинам.
     *
     * @param applicationContext Контекст приложения Spring.
     * @throws BeansException    Исключение, возникающее при проблемах с бинами Spring.
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}


