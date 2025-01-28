package com.example.inventory.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.inventory.repository.InventoryRepository;
import com.example.inventory.model.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class DeviceService implements InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    private static final Logger logger = LoggerFactory.getLogger(DeviceService.class);

    @Override
    public Inventory saveDevice(Inventory inventory) {
        logger.info("Saving device: {}", inventory);
        return inventoryRepository.save(inventory);
    }

    @Override
    public Optional<Inventory> getDevice(Long id) {
        logger.info("Fetching device with ID: {}", id);
        return inventoryRepository.findById(id);
    }

    @Override
    public void deleteDevice(Long id) {
        logger.info("Deleting device with ID: {}", id);
        inventoryRepository.deleteById(id);
    }

    @Override
    public Inventory modifyDevice(Long id, Inventory updatedInventory) {
        logger.info("Modifying device with ID: {}", id);
        updatedInventory.setId(id);
        return inventoryRepository.save(updatedInventory);
    }
}
