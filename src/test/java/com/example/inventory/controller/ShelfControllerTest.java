package com.example.inventory.controller;

import com.example.inventory.model.Device;
import com.example.inventory.model.Shelf;
import com.example.inventory.service.ShelfService;
import com.example.inventory.model.ShelfPosition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.*;
//import java.util.ArrayList;
//import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ShelfControllerTest {

    @InjectMocks
    private ShelfController shelfController;

    @Mock
    private ShelfService shelfService;

    private Shelf shelf;
    @BeforeEach
    void setUp() {
        Shelf shelf = new Shelf(1L, "Shelf1", "Wooden", "Live", null);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveShelf() {
        when(shelfService.saveShelf(shelf)).thenReturn(shelf);

        ResponseEntity<Shelf> response = shelfController.saveShelf(shelf);

        assertNotNull(response);
        assertEquals(shelf, response.getBody());
    }

    @Test
    void testGetShelfById() {
        when(shelfService.getShelf(1L)).thenReturn(Optional.ofNullable(shelf));

        ResponseEntity<Shelf> response = shelfController.getShelf(1L);
        assertNotNull(response);
        assertEquals(shelf, response.getBody());
    }

    @Test
    void testGetAllShelves() {
        List<Shelf> shelves = Arrays.asList(
                new Shelf(1L, "Shelf1", "Wooden", "Live"),
                new Shelf(2L, "Shelf2", "Metal", "Live")
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
        Device device = new Device(1L, "Device1", "Type1", "Live", new HashSet<>());
        ShelfPosition shelfPosition = new ShelfPosition(1L, "Position1", "Live");

        shelfController.addShelfPositionToDevice(1L, 1L);
        verify(shelfService, times(1)).addShelfPositionToDevice(1L, 1L);
    }

    @Test
    void testAddShelfToShelfPosition() {
        ShelfPosition shelfPosition = new ShelfPosition(1L, "Position1", "Live");

        shelfController.addShelfToShelfPosition(1L, 1L);
        verify(shelfService, times(1)).addShelfToShelfPosition(1L, 1L);
    }
}
