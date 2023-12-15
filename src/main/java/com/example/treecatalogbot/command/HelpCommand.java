package com.example.treecatalogbot.command;

import com.example.treecatalogbot.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Команда для отображения справочной информации о доступных командах бота.
 * Реализует интерфейс BotCommand для выполнения команды бота.
 */
@RequiredArgsConstructor
@Component
public class HelpCommand implements BotCommand {

    private final CategoryService categoryService;

    /**
     * Возвращает идентификатор данной команды.
     *
     * @return Строковый идентификатор команды.
     */
    @Override
    public String getCommandIdentifier() {
        return "/help";
    }

    /**
     * Выполняет команду для отображения справочной информации о доступных командах бота.
     *
     * @param args    Список аргументов команды (ожидается один аргумент - "/help").
     * @param chatId  Идентификатор чата, в котором производится выполнение команды.
     * @return        Результат выполнения команды в виде строки (список доступных команд).
     */
    @Override
    public String execute(List<String> args, long chatId) {
        if (args.size() != 1) {
            return "Неправильная команда, введите так: /help";
        } else {
            return categoryService.viewCommand();
        }
    }
}


