package com.example.treecatalogbot;

import com.example.treecatalogbot.entity.CategoryTree;
import com.example.treecatalogbot.repo.CategoryTreeRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CategoryTreeRepoTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CategoryTreeRepo categoryTreeRepo;

    @Test
    void testFindByName() {
        // Создаем тестовые данные
        CategoryTree categoryTree = new CategoryTree("TestNode", 123L);
        entityManager.persist(categoryTree);
        entityManager.flush();

        // Ищем узел по имени
        CategoryTree found = categoryTreeRepo.findByName("TestNode");

        // Проверяем, что найденный узел не null и соответствует нашему ожиданию
        assertNotNull(found);
        assertEquals("TestNode", found.getName());
    }

    @Test
    void testFindByParentIsNullAndChatId() {
        // Создаем тестовые данные
        CategoryTree root1 = new CategoryTree("Root1", 123L);
        CategoryTree child1 = new CategoryTree("Child1", 123L);
        child1.setParent(root1);

        entityManager.persist(root1);
        entityManager.persist(child1);
        entityManager.flush();

        // Ищем корневые узлы
        List<CategoryTree> rootNodes = categoryTreeRepo.findByParentIsNullAndChatId(123L);

        // Проверяем, что список не null и содержит корневой узел
        assertNotNull(rootNodes);
        assertEquals(1, rootNodes.size());
        assertEquals("Root1", rootNodes.get(0).getName());
    }

    @Test
    void testDeleteByChatIdAndName() {
        // Создаем тестовые данные
        CategoryTree categoryTree = new CategoryTree("ToDelete", 123L);
        entityManager.persist(categoryTree);
        entityManager.flush();

        // Удаляем узел
        categoryTreeRepo.deleteByChatIdAndName(123L, "ToDelete");

        // Проверяем, что узел удален
        CategoryTree deleted = categoryTreeRepo.findByName("ToDelete");
        assertNull(deleted);
    }
}

