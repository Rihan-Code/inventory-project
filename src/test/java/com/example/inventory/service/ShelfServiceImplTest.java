package com.example.inventory.service;

import com.example.inventory.model.Device;
import com.example.inventory.model.Shelf;
import com.example.inventory.model.ShelfPosition;
import com.example.inventory.repository.DeviceRepository;
import com.example.inventory.repository.ShelfPositionRepository;
import com.example.inventory.repository.ShelfRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class ShelfServiceImplTest {
    @Mock // creates a mock instance of a class
    private DeviceRepository deviceRepository;

    @Mock
    private ShelfRepository shelfRepository;

    @Mock
    private ShelfPositionRepository shelfPositionRepository;

    @InjectMocks // injects mock dependencies into the test object
    private ShelfServiceImpl shelfService;

    private Device device;
    private Shelf shelf;
    private ShelfPosition shelfPosition;

    @BeforeEach
    public void setUp() {
        device = new Device(1L, "Device1", "Type1", "Live", new HashSet<>());
        shelf = new Shelf(1L, "Shelf1", "Wooden", "Live");
        shelfPosition = new ShelfPosition(1L, "Position1", "Live");
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddShelfPositionToDevice() {
        when(deviceRepository.findById(1L)).thenReturn(Optional.of(device));
        when(shelfPositionRepository.findById(1L)).thenReturn(Optional.of(shelfPosition));

        shelfService.addShelfPositionToDevice(1L, 1L);
        assertNotNull(device.getShelfPositions());
        assertTrue(device.getShelfPositions().contains(shelfPosition));

        verify(deviceRepository, times(1)).save(device);
        verify(shelfPositionRepository, times(1)).save(shelfPosition);
    }

    @Test
    void testAddShelfToShelfPosition() {
        when(shelfRepository.findById(1L)).thenReturn(Optional.of(shelf));
        when(shelfPositionRepository.findById(1L)).thenReturn(Optional.of(shelfPosition));

        shelfService.addShelfToShelfPosition(1L, 1L);

        assertNotNull(shelfPosition.getShelf());
        assertEquals(shelf, shelfPosition.getShelf());

        verify(shelfPositionRepository, times(1)).save(shelfPosition);
        verify(shelfRepository, times(1)).save(shelf);
    }
}
