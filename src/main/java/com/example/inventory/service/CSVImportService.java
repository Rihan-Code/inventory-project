package com.example.inventory.service;

import com.example.inventory.model.Device;
import com.example.inventory.repository.DeviceRepository;
import lombok.Locked;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CSVImportService {
    private final DeviceRepository deviceRepository;

    public CSVImportService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public void importDevicesFromCSV(String filePath) {
        List<Device> newDevices = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
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

            deviceRepository.saveAll(newDevices);
            log.info("Devices imported from CSV successfully!");
        } catch (IOException e) {
            log.error("Error importing Devices from CSV: {}", e.getMessage());
        }
    }
}
