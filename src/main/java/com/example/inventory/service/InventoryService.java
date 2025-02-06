package com.example.inventory.service;

import java.util.*;

import com.example.inventory.model.Device;

public interface InventoryService {
    Optional<Device> saveDevice(Device device);
    Optional<Device> getDevice(Long id);

    Optional<Object> deleteDevice(Long id);
    Device modifyDevice(Long id, Device updatedDevice);


}
