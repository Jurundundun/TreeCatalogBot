package com.example.treecatalogbot.service;


import com.example.treecatalogbot.entity.CategoryTree;
import com.example.treecatalogbot.repo.CategoryTreeRepo;
import com.example.treecatalogbot.util.CategoryTreeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Сервис для управления структурой дерева категорий.
 * Содержит методы для добавления, удаления и просмотра категорий в дереве.
 */
@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryTreeRepo categoryRepository;
    private final CategoryTreeUtil categoryTreeUtil;

    /**
     * Добавляет элемент верхнего уровня в дерево категорий для указанного чата.
     *
     * @param elementName Имя добавляемого элемента.
     * @param chatId      Идентификатор чата.
     * @return Сообщение об успешном сохранении элемента.
     */
    public String addElement(String elementName, long chatId) {
        categoryRepository.save(new CategoryTree(elementName, chatId));
        return "Элемент " + elementName + " успешно сохранен";
    }

    /**
     * Добавляет дочерний элемент к родительскому элементу в дереве категорий для указанного чата.
     *
     * @param parentName Имя родительского элемента.
     * @param childName  Имя дочернего элемента.
     * @param chatId     Идентификатор чата.
     * @return Сообщение об успешном сохранении дочернего элемента или об ошибке, если родительский элемент не найден.
     */
    public String addElement(String parentName, String childName, long chatId) {
        CategoryTree parent = categoryRepository.findByName(parentName);
        if (parent != null) {
            CategoryTree child = new CategoryTree(childName, chatId);
            child.setParent(parent);
            categoryRepository.save(child);
            return "Элемент " + childName + " успешно сохранен";
        } else {
            return "Родитель с именем " + parentName + " не найден";
        }
    }

    /**
     * Удаляет элемент из дерева категорий для указанного чата.
     *
     * @param elementName Имя удаляемого элемента.
     * @param chatId      Идентификатор чата.
     * @return Сообщение об успешном удалении элемента или об ошибке, если элемент не найден.
     */
    public String removeElement(String elementName, long chatId) {
        CategoryTree element = categoryRepository.findByName(elementName);
        if (element != null) {
            categoryRepository.deleteByChatIdAndName(chatId, elementName);
            return "Элемент " + elementName + " успешно удален";
        } else {
            return "Элемент с именем " + elementName + " не найден";
        }
    }

    /**
     * Просмотр дерева категорий для указанного чата.
     *
     * @param chatId Идентификатор чата.
     * @return Строковое представление дерева категорий для указанного чата.
     */
    @Transactional
    public String viewTree(long chatId) {
        List<CategoryTree> rootNodes = categoryRepository.findByParentIsNullAndChatId(chatId);
        StringBuilder result = new StringBuilder();

        for (CategoryTree rootNode : rootNodes) {
            String treeStructure = categoryTreeUtil.viewTreeStructure(rootNode, "", true);
            result.append(treeStructure).append("\n");
        }
        return result.toString();
    }

    /**
     * Выводит список доступных команд и их описание.
     *
     * @return Список доступных команд и их описание.
     */
    public String viewCommand() {
        return "Доступные команды:\n" +
                "/addElement название элемента - добавляет корневой элемент, если у него нет родителя.\n" +
                "/addElement родительский элемент дочерний элемент - добавляет дочерний элемент к существующему.\n" +
                "/viewTree - просмотр всего дерева категорий в структурированном виде.\n" +
                "/removeElement название элемента - удаляет элемент, включая его дочерние элементы.\n" +
                "/help - выводит список всех доступных команд и их описание.";
    }
}

