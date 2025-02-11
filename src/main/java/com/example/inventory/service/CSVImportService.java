package com.example.inventory.service;

import com.example.inventory.model.Device;
import com.example.inventory.repository.DeviceRepository;
import lombok.Locked;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CSVImportService {
    private final DeviceRepository deviceRepository;
    public CSVImportService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public ResponseEntity<Void> importDevicesFromCSV(MultipartFile file) {
//        if(file.isEmpty()) {
//           throw new RuntimeException("File is empty!");
//        }
        List<Device> newDevices = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            log.info("Reading CSV file: {}", file.getOriginalFilename());

            String line;
            boolean firstLine = true;

            while((line = reader.readLine()) != null) {
                // skipping header line
                if(firstLine) {
                    firstLine = false;
                    continue;
                }

                String[] data = line.split(",");
                if(data.length < 4) continue;

                Device device = new Device();
                device.setDeviceId(Long.parseLong(data[0]));
                device.setName(data[1]);
                device.setDeviceType(data[2]);
                device.setStatus(data[3]);

                newDevices.add(device);
            }

            if(!newDevices.isEmpty()) {
                deviceRepository.saveAll(newDevices);
            }

            log.info("Devices imported from CSV successfully!");
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IOException e) {
            log.error("Error importing Devices from CSV: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
