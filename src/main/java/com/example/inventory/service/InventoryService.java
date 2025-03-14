package com.example.inventory.service;

import java.util.*;

import com.example.inventory.model.Device;

public interface InventoryService {
    Optional<Device> saveDevice(Device device);
    Optional<Device> getDevice(Long id);
    Optional<String> deleteDevice(Long id);
    Optional<Device> updateDevice(Long id, Device updatedDevice);


}
