package com.example.inventory.service;

import com.example.inventory.model.Inventory;
import com.example.inventory.model.ShelfPosition;
import com.example.inventory.repository.InventoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class DeviceServiceTest {
    @InjectMocks
    private DeviceService deviceService;

    @Mock
    private InventoryRepository inventoryRepository;

    private Inventory device;

    @BeforeEach
    public void setUp() {
        device = new Inventory(1L, "Device1", "Type1", null);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveDevice() {
        when(inventoryRepository.save(device)).thenReturn(device);
        deviceService.saveDevice(device);
        verify(inventoryRepository, times(1)).save(device);
    }

    @Test
    void testGetDevice() {
        when(inventoryRepository.findById(1L)).thenReturn(Optional.of(device));
        deviceService.getDevice(1L);
        verify(inventoryRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteDevice() {
        deviceService.deleteDevice(1L);
        verify(inventoryRepository, times(1)).deleteById(1L);
    }

    @Test
    void testModifyDevice() {
        when(inventoryRepository.save(device)).thenReturn(device);
        device.setName("DeviceA");
        deviceService.modifyDevice(1L, device);
        verify(inventoryRepository, times(1)).save(device);
    }
}




















