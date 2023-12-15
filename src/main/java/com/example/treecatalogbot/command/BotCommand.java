package com.example.treecatalogbot.command;

import java.util.List;

/**
 * Интерфейс, определяющий методы, необходимые для выполнения команд бота.
 */
public interface BotCommand {

    /**
     * Выполняет команду бота с аргументами и определенным чатом.
     *
     * @param args    Список аргументов для выполнения команды.
     * @param chatId  Идентификатор чата, в котором будет выполнена команда.
     * @return        Результат выполнения команды в виде строки.
     */
    String execute(List<String> args, long chatId);

    /**
     * Получает идентификатор команды для определения ее типа или назначения.
     *
     * @return  Строковый идентификатор команды.
     */
    String getCommandIdentifier();
}
