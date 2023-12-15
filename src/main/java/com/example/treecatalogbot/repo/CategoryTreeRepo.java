package com.example.treecatalogbot.repo;

import com.example.treecatalogbot.entity.CategoryTree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Репозиторий для работы с сущностью CategoryTree, отвечающей за узлы каталога.
 * Предоставляет методы для выполнения операций с таблицей category_tree(удаление, создание, поиск).
 */
@Repository
public interface CategoryTreeRepo extends JpaRepository<CategoryTree, UUID> {

    /**
     * Находит узел каталога по его имени.
     *
     * @param name Имя узла каталога.
     * @return Найденный узел каталога.
     */
    CategoryTree findByName(String name);

    /**
     * Находит все узлы корневого уровня (у которых нет родителя) для конкретного чата.
     *
     * @param chatId Идентификатор чата.
     * @return Список узлов корневого уровня для указанного чата.
     */
    List<CategoryTree> findByParentIsNullAndChatId(long chatId);

    /**
     * Удаляет узел каталога по идентификатору чата и имени.
     *
     * @param chatId Идентификатор чата.
     * @param name   Имя узла каталога для удаления.
     */
    void deleteByChatIdAndName(long chatId, String name);
}
