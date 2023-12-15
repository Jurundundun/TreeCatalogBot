package com.example.treecatalogbot.util;

import com.example.treecatalogbot.command.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Утилитарный класс для работы с ботом Telegram.
 */
public class TelegramBotUtil {

    /**
     * Определение команды из текста сообщения.
     *
     * @param text        Текст сообщения.
     * @param commandMap  Словарь, сопоставляющий команды и объекты BotCommand.
     * @return            Объект BotCommand, соответствующий команде в тексте сообщения.
     */
    public static BotCommand determineCommand(String text, Map<String, BotCommand> commandMap) {
        String[] tokens = text.split("\\s+");
        String commandKey = tokens[0];

        return commandMap.getOrDefault(commandKey, null);
    }

    /**
     * Разбиение текста сообщения на отдельные строки.
     *
     * @param text  Текст сообщения.
     * @return      Список токенов (слов) из текста сообщения.
     */
    public static List<String> extractCommand(String text) {
        String[] tokens = text.split("\\s+");
        return Arrays.asList(tokens);
    }

    /**
     * Создание сообщения для отправки.
     *
     * @param chatId  ID чата, куда отправляется сообщение.
     * @param text    Текст сообщения.
     * @return        Объект SendMessage для отправки сообщения.
     */
    public static SendMessage createMessage(long chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(text);
        return sendMessage;
    }
}

