package com.example.inventory.controller;

import com.example.inventory.model.Inventory;
import com.example.inventory.model.ShelfPosition;
import com.example.inventory.service.DeviceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DeviceControllerTest {
    @InjectMocks
    private DeviceController deviceController;

    @Mock
    private DeviceService deviceService;

    private Inventory device;

    @BeforeEach
    public void setUp() {
        device = new Inventory(1L, "Device1", "Type1", new ArrayList<>());
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveDevice() {
        when(deviceService.saveDevice(device)).thenReturn(device);
        ResponseEntity<Inventory> response = deviceController.saveDevice(device);
        assertNotNull(response);
        assertEquals(device, response.getBody());
    }

    @Test
    void testGetDevice() {
        when(deviceService.getDevice(1L)).thenReturn(Optional.ofNullable(device));
        ResponseEntity<Inventory> response = deviceController.getDevice(1L);
        assertNotNull(response);
        assertEquals(device, response.getBody());
    }

    @Test
    void testDeleteDevice() {
        deviceController.deleteDevice(1L);
        verify(deviceService, times(1)).deleteDevice(1L);
    }
}