package com.example.inventory.controller;

import com.example.inventory.model.Inventory;
import com.example.inventory.model.Shelf;
import com.example.inventory.service.ShelfService;
import com.example.inventory.model.ShelfPosition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.*;
//import java.util.ArrayList;
//import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ShelfControllerTest {

    @InjectMocks
    private ShelfController shelfController;

    @Mock
    private ShelfService shelfService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveShelf() {
        Shelf shelf = new Shelf(1L, "Shelf1", "Wooden");
        when(shelfService.saveShelf(shelf)).thenReturn(shelf);

        ResponseEntity<Shelf> response = shelfController.saveShelf(shelf);

        assertNotNull(response);
        assertEquals(shelf, response.getBody());
    }

    @Test
    void testGetShelfById() {
        Shelf shelf = new Shelf(1L, "Shelf1", "Wooden");
        when(shelfService.getShelf(1L)).thenReturn(Optional.of(shelf));

        ResponseEntity<Shelf> response = shelfController.getShelf(1L);
        assertNotNull(response);
        assertEquals(shelf, response.getBody());
    }

    @Test
    void testGetAllShelves() {
        List<Shelf> shelves = Arrays.asList(
                new Shelf(1L, "Shelf1", "Wooden"),
                new Shelf(2L, "Shelf2", "Metal")
        );
        when(shelfService.getAllShelves()).thenReturn(Optional.of(shelves));

        ResponseEntity<List<Shelf>> response = shelfController.getAllShelves();
        assertNotNull(response);
        assertEquals(shelves, response.getBody());
    }

    @Test
    void testDeleteShelf() {
        shelfController.deleteShelf(1L);
        verify(shelfService, times(1)).deleteShelf(1L);
    }

    @Test
    void testAddShelfPositionToDevice() {
        Inventory device = new Inventory(1L, "Device1", "Type1", null);
        ShelfPosition shelfPosition = new ShelfPosition(1L, "Position1");

        shelfController.addShelfPositionToDevice(1L, 1L);
        verify(shelfService, times(1)).addShelfPositionToDevice(1L, 1L);
    }

    @Test
    void testAddShelfToShelfPosition() {
        Shelf shelf = new Shelf(1L, "Shelf1", "Wooden");
        ShelfPosition shelfPosition = new ShelfPosition(1L, "Position1");

        shelfController.addShelfToShelfPosition(1L, 1L);
        verify(shelfService, times(1)).addShelfToShelfPosition(1L, 1L);
    }
}
