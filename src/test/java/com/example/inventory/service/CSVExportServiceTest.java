package com.example.inventory.service;

import com.example.inventory.model.Device;
import com.example.inventory.repository.DeviceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class CSVExportServiceTest {
    @Mock
    private DeviceRepository deviceRepository;

    @InjectMocks
    private CSVExportService csvExportService;

    private List<Device> mockDevices;

    @BeforeEach
    void  setUp() {
        MockitoAnnotations.openMocks(this);

        Device device1 = new Device(101L, "Device1", "Type1", "Live", null);
        Device device2 = new Device(102L, "Device2", "Type2", "Live", null);
        mockDevices = Arrays.asList(device1, device2);
    }

    @Test
    void testExportDevicesToCSV() {
        when(deviceRepository.findAll()).thenReturn(mockDevices);

        String fileName = csvExportService.exportDeviceToCSV();
        assertNotNull(fileName);
        File file = new File(fileName);
        assertTrue(file.exists());

        verify(deviceRepository, times(1)).findAll();

        file.delete();
    }
}
