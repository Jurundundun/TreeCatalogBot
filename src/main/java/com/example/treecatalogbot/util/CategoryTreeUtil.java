package com.example.treecatalogbot.util;

import com.example.treecatalogbot.entity.CategoryTree;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.stereotype.Component;


import java.util.List;

/**
 * Утилитарный класс для работы с объектами CategoryTree.
 * Предоставляет методы для работы с деревьями категорий.
 */
@Component
@RequiredArgsConstructor
public class CategoryTreeUtil {

    /**
     * Менеджер сущностей для работы с JPA.
     */
    @PersistenceContext
    private final EntityManager entityManager;

    /**
     * Метод для генерации текстового представления дерева категорий.
     *
     * @param node   Корневой узел дерева категорий.
     * @param prefix Префикс для отображения структуры дерева.
     * @param isTail Флаг, указывающий, является ли текущий узел "листьевым".
     * @return Текстовое представление структуры дерева категорий.
     */
    public String viewTreeStructure(CategoryTree node, String prefix, boolean isTail) {
        // Построение строкового представления дерева категорий
        StringBuilder builder = new StringBuilder();
        // Добавление имени текущего узла с соответствующим префиксом
        builder.append(prefix).append(isTail ? "└── " : "├── ").append(node.getName()).append("\n");

        try (Session session = entityManager.unwrap(Session.class)) {
            // Проверка на инициализацию ленивой коллекции children
            if (!Hibernate.isInitialized(node.getChildren())) {
                // Если коллекция не инициализирована, происходит загрузка узла и инициализация children
                node = session.load(CategoryTree.class, node.getId());
                Hibernate.initialize(node.getChildren());
            }

            List<CategoryTree> children = node.getChildren();
            // Если children не пустой, добавляем их в строковое представление дерева
            if (children != null && !children.isEmpty()) {
                for (int i = 0; i < children.size() - 1; i++) {
                    // Рекурсивный вызов метода для добавления дочерних узлов
                    builder.append(viewTreeStructure(children.get(i), prefix + (isTail ? "    " : "│   "), false));
                }
                // Добавление последнего дочернего узла
                if (children.size() >= 1) {
                    builder.append(viewTreeStructure(children.get(children.size() - 1), prefix + (isTail ? "    " : "│   "), true));
                }
            }
        } catch (Exception e) {
            // Обработка возможных исключений
            e.printStackTrace();
        }
        return builder.toString();
    }

}

