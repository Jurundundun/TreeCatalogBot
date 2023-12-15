package com.example.treecatalogbot.service;

import com.example.treecatalogbot.command.*;
import com.example.treecatalogbot.util.CommandProvider;
import com.example.treecatalogbot.util.TelegramBotUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.Map;

/**
 * Сервис Telegram бота, реализующий обработку и выполнение команд пользователя.
 */
@Component
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {

    @Value("${telegram.bot.token}")
    private String token;
    @Value("${telegram.bot.name}")
    private String name;

    /**
     * Обрабатывает полученные обновления от Telegram и выполняет соответствующие команды пользователя.
     *
     * @param update Полученное обновление от Telegram.
     */
    @Override
    public void onUpdateReceived(Update update) {
        // Получаем карту команд из CommandProvider
        Map<String, BotCommand> commandMap = CommandProvider.getAllBotCommands();

        // Проверяем, есть ли обновление и текст сообщения
        if (update.hasMessage() && update.getMessage().hasText()) {
            Message message = update.getMessage();
            String text = message.getText();
            long chatId = message.getChatId();

            // Извлекаем аргументы команды из текста сообщения
            List<String> args = TelegramBotUtil.extractCommand(text);
            String commandKey = args.get(0);

            // Определяем, какая команда вызвана
            BotCommand command = TelegramBotUtil.determineCommand(commandKey, commandMap);

            // Если команда найдена, выполняем её
            if (command != null) {
                String response = command.execute(args, chatId);
                SendMessage sendMessage = TelegramBotUtil.createMessage(chatId, response);
                try {
                    execute(sendMessage); // Отправляем ответ пользователю
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else {
                // Если команда не найдена, отправляем сообщение об ошибке
                SendMessage sendMessage = TelegramBotUtil.createMessage(chatId, "Неизвестная команда: " + commandKey);
                try {
                    execute(sendMessage); // Отправляем сообщение о неизвестной команде
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Override
    public String getBotUsername() {
        return name;
    }

    @Override
    public String getBotToken() {
        return token;
    }
}
