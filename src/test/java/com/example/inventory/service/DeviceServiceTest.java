package com.example.inventory.service;

import com.example.inventory.model.Device;
import com.example.inventory.repository.DeviceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.mockito.Mockito.*;


public class DeviceServiceTest {
    @InjectMocks
    private DeviceService deviceService;

    @Mock
    private DeviceRepository deviceRepository;

    private Device device;

    @BeforeEach
    public void setUp() {
        device = new Device(1L, "Device1", "Type1", "Live", new HashSet<>());
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveDevice() {
        when(deviceRepository.save(device)).thenReturn(device);
        deviceService.saveDevice(device);
        verify(deviceRepository, times(1)).save(device);
    }

    @Test
    void testGetDevice() {
        when(deviceRepository.findById(1L)).thenReturn(Optional.of(device));
        deviceService.getDevice(1L);
        verify(deviceRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteDevice() {
        when(deviceRepository.findById(1L)).thenReturn(Optional.of(device));
        deviceService.deleteDevice(1L);
        verify(deviceRepository, times(1)).findById(1L);
    }

    @Test
    void testModifyDevice() {
        when(deviceRepository.findById(1L)).thenReturn(Optional.of(device));

        deviceService.modifyDevice(1L, device);
        verify(deviceRepository, times(1)).save(device);
    }
}




















