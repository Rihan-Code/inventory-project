package com.example.inventory.controller;

import com.example.inventory.service.CSVExportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


public class CSVControllerTest {
    @InjectMocks
    private CSVController csvController;

    @Mock
    private CSVExportService csvExportService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testExportCSV() throws IOException {
        String fileName = "test_devices.csv";
        File file = new File(fileName);
        file.createNewFile();

        when(csvExportService.exportDeviceToCSV()).thenReturn(fileName);

        ResponseEntity<InputStreamResource> response = csvController.exportDevicesToCSV();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getHeaders().getContentDisposition().toString().contains("test_devices.csv"));

        verify(csvExportService, times(1)).exportDeviceToCSV();

        file.delete();
    }
}
