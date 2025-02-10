package com.example.inventory.controller;

import com.example.inventory.model.Device;
import com.example.inventory.service.CSVExportService;
import com.example.inventory.service.CSVImportService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/csv")
public class CSVController {
    private final CSVExportService csvExportService;
    private final CSVImportService csvImportService;
    CSVController(CSVExportService csvExportService, CSVImportService csvImportService) {
        this.csvExportService = csvExportService;
        this.csvImportService = csvImportService;
    }

    @GetMapping("/export")
    public ResponseEntity<InputStreamResource> exportDevicesToCSV() throws FileNotFoundException {
        String fileName = csvExportService.exportDeviceToCSV();

        if(fileName == null) {
            return ResponseEntity.internalServerError().build();
        }

        File file = new File(fileName); // will create file object
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file)); // to read the file contents as a stream

        // content-disposition: instructs the browser to doenload the file with the correct filename
        // body: containe the file as resource stream
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+fileName)
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(resource);
    }

    @PostMapping("/append")
    public ResponseEntity<String> appendDevices(@RequestBody List<Device> devices) {
        csvExportService.appendNewDevicesToCSV(devices);
        return ResponseEntity.ok("New Devices added to CSV");
    }

    @PostMapping("/import")
    public ResponseEntity<String> importDevices(@RequestParam("file") MultipartFile file) {
        if(file.isEmpty()) return ResponseEntity.badRequest().body("No file upload!");

        try{
            File csvFile = new File("uploaded_devices.csv");
            file.transferTo(csvFile);
            csvImportService.importDevicesFromCSV(csvFile.getAbsolutePath());
            return ResponseEntity.ok("Devices Imported successfully!");
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
