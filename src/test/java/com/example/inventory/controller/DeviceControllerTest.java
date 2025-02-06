package com.example.inventory.controller;

import com.example.inventory.model.Device;
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

    private Device device;

    @BeforeEach
    public void setUp() {
        device = new Device(1L, "Device1", "Type1", "Live", new HashSet<>());
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveDevice() {
        when(deviceService.saveDevice(device)).thenReturn(Optional.ofNullable(device));
        ResponseEntity<Optional<Device>> response = deviceController.saveDevice(device);
        assertNotNull(response);
//        assertEquals(device, response.getBody().get());
    }

    @Test
    void testGetDevice() {
        when(deviceService.getDevice(1L)).thenReturn(Optional.ofNullable(device));
        ResponseEntity<Device> response = deviceController.getDevice(1L);
        assertNotNull(response);
        assertEquals(device, response.getBody());
    }

    @Test
    void testDeleteDevice() {
        deviceController.deleteDevice(1L);
        verify(deviceService, times(1)).deleteDevice(1L);
    }
}