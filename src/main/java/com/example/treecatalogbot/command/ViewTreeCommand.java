package com.example.treecatalogbot.command;

import com.example.treecatalogbot.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Команда для отображения древовидной структуры каталога.
 * Реализует интерфейс BotCommand для выполнения команды бота.
 */
@Component
@RequiredArgsConstructor
public class ViewTreeCommand implements BotCommand {

    private final CategoryService categoryService;

    /**
     * Возвращает идентификатор данной команды.
     *
     * @return Строковый идентификатор команды.
     */
    @Override
    public String getCommandIdentifier() {
        return "/viewTree";
    }

    /**
     * Выполняет команду для отображения древовидной структуры каталога.
     *
     * @param args    Список аргументов команды (ожидается один аргумент - "/viewTree").
     * @param chatId  Идентификатор чата, в котором производится выполнение команды.
     * @return        Результат выполнения команды в виде строки (дерево каталога).
     */
    @Override
    public String execute(List<String> args, long chatId) {
        if (args.size() != 1) {
            return "Неправильная команда, введите так: /viewTree";
        } else {
            return categoryService.viewTree(chatId);
        }
    }
}
