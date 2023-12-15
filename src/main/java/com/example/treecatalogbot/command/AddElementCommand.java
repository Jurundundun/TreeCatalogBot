package com.example.treecatalogbot.command;

import com.example.treecatalogbot.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Команда для добавления элемента в каталог.
 * Реализует интерфейс BotCommand для выполнения команды бота.
 */
@Component
@RequiredArgsConstructor
public class AddElementCommand implements BotCommand {

    private final CategoryService categoryService;

    /**
     * Возвращает идентификатор данной команды.
     *
     * @return Строковый идентификатор команды.
     */
    @Override
    public String getCommandIdentifier() {
        return "/addElement";
    }

    /**
     * Выполняет команду добавления элемента в каталог.
     *
     * @param args    Список аргументов команды.
     * @param chatId  Идентификатор чата, в котором производится выполнение команды.
     * @return        Результат выполнения команды в виде строки.
     */
    @Override
    public String execute(List<String> args, long chatId) {
        String childName, elementName;

        // Проверка количества аргументов команды для определения ее выполнения
        if (args.size() < 2 || args.size() > 3) {
            return "Неправильная команда, введите так: /addElement название_элемента или /addElement название_родителя название_ребенка";
        } else if (args.size() == 2) {
            // Выполнение команды для добавления элемента без указания родительского элемента
            elementName = args.get(1);
            return categoryService.addElement(elementName, chatId);
        } else {
            // Выполнение команды для добавления элемента с указанием родительского элемента
            elementName = args.get(1);
            childName = args.get(2);
            return categoryService.addElement(elementName, childName, chatId);
        }
    }
}

