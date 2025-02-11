package com.example.inventory.service;

import com.example.inventory.model.Device;
import com.example.inventory.repository.DeviceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.anyList;

public class CSVImportServiceTest {

    @Mock
    private DeviceRepository deviceRepository;

    @InjectMocks
    private CSVImportService csvImportService;

    private MockMultipartFile mockMultipartFile;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        String csvContent = "deviceId,name,deviceType,status\n101,Device1,Type1,Live\n102,Device2,Type2,Live";
        mockMultipartFile = new MockMultipartFile("file", "devices.csv", "text/csv", csvContent.getBytes());
    }

    @Test
    void testImportDevicesFromCSV_EmptyFile() {
        MockMultipartFile emptyFile = new MockMultipartFile("file", "empty.csv", "text/csv", "".getBytes());
        csvImportService.importDevicesFromCSV(emptyFile);

        verify(deviceRepository, never()).saveAll(anyList());
    }

    @Test
    void testImportDevicesFromCSV() {
//        when(deviceRepository.saveAll((List<Device>) mockMultipartFile)).thenReturn((List<Device>) mockMultipartFile);
        csvImportService.importDevicesFromCSV(mockMultipartFile);
        verify(deviceRepository, times(1)).saveAll(anyList());
    }

    @Test
    void testImportDevicesFromCSV_IOException() throws IOException {
        MockMultipartFile corruptFile = mock(MockMultipartFile.class);
        when(corruptFile.getInputStream()).thenThrow(new RuntimeException("File read error"));

        assertThrows(RuntimeException.class, () -> csvImportService.importDevicesFromCSV(corruptFile));
    }
}
