package com.example.inventory.service;

import java.util.Optional;
import com.example.inventory.model.Inventory;

public interface InventoryService {
    Inventory saveDevice(Inventory inventory);
    Optional<Inventory> getDevice(Long id);

    void deleteDevice(Long id);
    Inventory modifyDevice(Long id, Inventory updatedInventory);
}
