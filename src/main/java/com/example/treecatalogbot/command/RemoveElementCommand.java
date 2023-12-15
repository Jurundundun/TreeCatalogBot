package com.example.treecatalogbot.command;

import com.example.treecatalogbot.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Команда для удаления элемента из каталога.
 * Реализует интерфейс BotCommand для выполнения команды бота.
 */
@Component
@RequiredArgsConstructor
public class RemoveElementCommand implements BotCommand {

    private final CategoryService categoryService;

    /**
     * Возвращает идентификатор данной команды.
     *
     * @return Строковый идентификатор команды.
     */
    @Override
    public String getCommandIdentifier() {
        return "/removeElement";
    }

    /**
     * Выполняет команду для удаления элемента из каталога.
     *
     * @param args    Список аргументов команды (ожидается два аргумента: "/removeElement" и название_элемента).
     * @param chatId  Идентификатор чата, в котором производится выполнение команды.
     * @return        Результат выполнения команды в виде строки.
     */
    @Override
    public String execute(List<String> args, long chatId) {
        if (args.size() != 2) {
            return "Неправильная команда, введите так: /removeElement название_элемента";
        } else {
            String elementName = args.get(1);
            return categoryService.removeElement(elementName, chatId);
        }
    }
}


