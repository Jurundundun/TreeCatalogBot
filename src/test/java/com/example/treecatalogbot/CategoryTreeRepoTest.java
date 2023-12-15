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
        // ������� �������� ������
        CategoryTree categoryTree = new CategoryTree("TestNode", 123L);
        entityManager.persist(categoryTree);
        entityManager.flush();

        // ���� ���� �� �����
        CategoryTree found = categoryTreeRepo.findByName("TestNode");

        // ���������, ��� ��������� ���� �� null � ������������� ������ ��������
        assertNotNull(found);
        assertEquals("TestNode", found.getName());
    }

    @Test
    void testFindByParentIsNullAndChatId() {
        // ������� �������� ������
        CategoryTree root1 = new CategoryTree("Root1", 123L);
        CategoryTree child1 = new CategoryTree("Child1", 123L);
        child1.setParent(root1);

        entityManager.persist(root1);
        entityManager.persist(child1);
        entityManager.flush();

        // ���� �������� ����
        List<CategoryTree> rootNodes = categoryTreeRepo.findByParentIsNullAndChatId(123L);

        // ���������, ��� ������ �� null � �������� �������� ����
        assertNotNull(rootNodes);
        assertEquals(1, rootNodes.size());
        assertEquals("Root1", rootNodes.get(0).getName());
    }

    @Test
    void testDeleteByChatIdAndName() {
        // ������� �������� ������
        CategoryTree categoryTree = new CategoryTree("ToDelete", 123L);
        entityManager.persist(categoryTree);
        entityManager.flush();

        // ������� ����
        categoryTreeRepo.deleteByChatIdAndName(123L, "ToDelete");

        // ���������, ��� ���� ������
        CategoryTree deleted = categoryTreeRepo.findByName("ToDelete");
        assertNull(deleted);
    }
}

