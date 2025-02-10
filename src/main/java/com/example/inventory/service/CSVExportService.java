package com.example.inventory.service;

import com.example.inventory.model.Device;
import com.example.inventory.repository.DeviceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.io.*;
import java.util.*;

@Service
@Slf4j
public class CSVExportService {
    private final DeviceRepository deviceRepository;

    public CSVExportService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public String exportDeviceToCSV() {
        List<Device> devices = deviceRepository.findAll();
        String fileName = "devices.csv";

        try(FileWriter writer = new FileWriter(fileName, false)) {
            writer.append("deviceId,name,deviceType,status\n");

            for(Device device : devices) {
                writer.append(device.getDeviceId().toString())
                        .append(",")
                        .append(device.getName())
                        .append(",")
                        .append(device.getDeviceType())
                        .append(",")
                        .append(device.getStatus())
                        .append("\n");
            }
            log.info("CSV file for Device created {} successfully!", fileName);
            return fileName;
        }catch (IOException e) {
            log.error("Error in exporting CSV file: {}",e.getMessage());
            return e.getMessage();
        }
    }

    public void appendNewDevicesToCSV(List<Device> newDevices) {
        String fileName = "devices.csv";

        Long lastDeviceId = findLastDeviceId(fileName);

        try(FileWriter writer = new FileWriter(fileName, true)) {
            for(Device device : newDevices) {
                device.setDeviceId(++lastDeviceId);

                writer.append(device.getDeviceId().toString())
                        .append(",")
                        .append(device.getName())
                        .append(",")
                        .append(device.getDeviceType())
                        .append(",")
                        .append(device.getStatus())
                        .append("\n");
            }
            log.info("New Devices appended to the CSV successfully!");
        } catch (IOException e) {
            log.error("Error occurred while appending into CSV file: {}", e.getMessage());
        }
    }

    public Long findLastDeviceId(String fileName) {
        long lastDeviceId = 101L;

        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            String lastLine = "";

            while((line = reader.readLine()) != null) lastLine = line;

            if(!lastLine.isEmpty() && !lastLine.startsWith("deviceId")) {
                String[] columns = lastLine.split(",");
                lastDeviceId = Long.parseLong(columns[0]);
            }
        } catch (IOException e) {
            log.warn("Could not determine the last deviceId, starting from 101");
        }
        return lastDeviceId;
    }
}
