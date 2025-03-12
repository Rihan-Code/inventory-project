package com.example.inventory.service;

import com.example.inventory.model.Device;
import com.example.inventory.repository.DeviceRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@Slf4j 
public class DeviceService implements InventoryService {
//    @Autowired
//    private InventoryRepository inventoryRepository;
    private final DeviceRepository deviceRepository;
    DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Override
    public Optional<Device> saveDevice(Device device){
        log.info("Saving device: {}", device);
        return Optional.of(deviceRepository.save(device));
    }

    @Override
    public Optional<Device> getDevice(Long id) {
        return Optional.ofNullable(deviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Device not found!")));
    }

    public List<Device> getAllDevices() {
        return Optional.of(deviceRepository.findAll())
                .orElseThrow(() -> new RuntimeException("Devices not found!"));
    }

    @Override
    public Optional<String> deleteDevice(Long id) {
        Device device = deviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Device not found!"));
//        @Query("MATCH(d:Device)-")
        device.setStatus("Decommissioned");
        deviceRepository.save(device);
        return Optional.of("Device soft deleted!");
    }

    @Override
    public Optional<Device> updateDevice(Long id, Device updatedDevice) {
        Device device = deviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Device not found!"));
        device.setName(updatedDevice.getName());
        device.setDeviceType(updatedDevice.getDeviceType());
        device.setStatus(updatedDevice.getStatus());
        deviceRepository.save(device);
        return Optional.of(device);
    }
}
