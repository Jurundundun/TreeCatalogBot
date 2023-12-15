package com.example.treecatalogbot;

import com.example.treecatalogbot.entity.CategoryTree;
import com.example.treecatalogbot.repo.CategoryTreeRepo;
import com.example.treecatalogbot.service.CategoryService;
import com.example.treecatalogbot.util.CategoryTreeUtil;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    @Mock
    private CategoryTreeRepo categoryRepository;

    @Mock
    private CategoryTreeUtil categoryTreeUtil;

    @InjectMocks
    private CategoryService categoryService;

    public CategoryServiceTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addElement_WithElementName_ShouldReturnSuccessMessage() {
        when(categoryRepository.save(any(CategoryTree.class))).thenReturn(new CategoryTree("Test", 1));

        String response = categoryService.addElement("Test", 1);

        assertEquals("Элемент Test успешно сохранен", response);
    }

    @Test
    public void addElement_WithParentAndChildNames_ShouldReturnSuccessMessage() {
        CategoryTree parent = new CategoryTree("Parent", 1);
        when(categoryRepository.findByName("Parent")).thenReturn(parent);
        when(categoryRepository.save(any(CategoryTree.class))).thenReturn(new CategoryTree("Child", 1));

        String response = categoryService.addElement("Parent", "Child", 1);

        assertEquals("Элемент Child успешно сохранен", response);
    }

    @Test
    public void addElement_WithUnknownParent_ShouldReturnErrorMessage() {
        when(categoryRepository.findByName("Unknown")).thenReturn(null);

        String response = categoryService.addElement("Unknown", "Child", 1);

        assertEquals("Родитель с именем Unknown не найден", response);
    }

    @Test
    public void removeElement_WithValidName_ShouldReturnSuccessMessage() {
        CategoryTree element = new CategoryTree("Element", 1);
        when(categoryRepository.findByName("Element")).thenReturn(element);

        String response = categoryService.removeElement("Element", 1);

        assertEquals("Элемент Element успешно удален", response);
        verify(categoryRepository, times(1)).deleteByChatIdAndName(eq(1L), eq("Element"));
    }

    @Test
    public void removeElement_WithUnknownName_ShouldReturnErrorMessage() {
        when(categoryRepository.findByName("Unknown")).thenReturn(null);

        String response = categoryService.removeElement("Unknown", 1);

        assertEquals("Элемент с именем Unknown не найден", response);
        verify(categoryRepository, never()).deleteByChatIdAndName(anyLong(), anyString());
    }

    @Test
    public void viewTree_WithValidChatId_ShouldReturnTreeView() {
        CategoryTree rootNode = new CategoryTree("Root", 1);
        when(categoryRepository.findByParentIsNullAndChatId(1)).thenReturn(Collections.singletonList(rootNode));
        when(categoryTreeUtil.viewTreeStructure(any(), anyString(), anyBoolean())).thenReturn("Tree structure");

        String response = categoryService.viewTree(1);

        assertEquals("Tree structure\n", response);
        verify(categoryTreeUtil, times(1)).viewTreeStructure(eq(rootNode), anyString(), eq(true));
    }

    @Test
    public void viewTree_WithInvalidChatId_ShouldReturnEmptyString() {
        when(categoryRepository.findByParentIsNullAndChatId(2)).thenReturn(Collections.emptyList());

        String response = categoryService.viewTree(2);

        assertEquals("", response);
        verify(categoryTreeUtil, never()).viewTreeStructure(any(), anyString(), anyBoolean());
    }

    @Test
    public void viewCommand_ShouldReturnListOfAvailableCommands() {
        String expectedCommands = "Доступные команды:\n" +
                "/addElement название элемента - добавляет корневой элемент, если у него нет родителя.\n" +
                "/addElement родительский элемент дочерний элемент - добавляет дочерний элемент к существующему.\n" +
                "/viewTree - просмотр всего дерева категорий в структурированном виде.\n" +
                "/removeElement название элемента - удаляет элемент, включая его дочерние элементы.\n" +
                "/help - выводит список всех доступных команд и их описание.";

        String response = categoryService.viewCommand();

        assertEquals(expectedCommands, response);
    }
}


