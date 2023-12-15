package com.example.treecatalogbot;

import com.example.treecatalogbot.command.RemoveElementCommand;
import com.example.treecatalogbot.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class RemoveElementCommandTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private RemoveElementCommand removeElementCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getCommandIdentifier_ReturnsCorrectIdentifier() {
        // Arrange

        // Act
        String identifier = removeElementCommand.getCommandIdentifier();

        // Assert
        assertEquals("/removeElement", identifier);
    }

    @Test
    void execute_WithInvalidArguments_ReturnsErrorMessage() {
        // Arrange
        List<String> args = Arrays.asList("/removeElement");

        // Act
        String result = removeElementCommand.execute(args, 123L);

        // Assert
        assertEquals("Неправильная команда, введите так: /removeElement название_элемента", result);
    }

    @Test
    void execute_WithValidArguments_CallsCategoryServiceRemoveElementAndReturnsResult() {
        // Arrange
        List<String> args = Arrays.asList("/removeElement", "elementName");
        long chatId = 123L;
        String expectedResult = "Успешно удалили элемент";

        when(categoryService.removeElement("elementName", chatId)).thenReturn(expectedResult);

        // Act
        String result = removeElementCommand.execute(args, chatId);

        // Assert
        assertEquals(expectedResult, result);
    }
}

