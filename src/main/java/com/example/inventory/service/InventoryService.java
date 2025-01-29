package com.example.inventory.service;

import java.util.*;
import com.example.inventory.model.Inventory;
import com.example.inventory.model.ShelfPosition;

public interface InventoryService {
    Inventory saveDevice(Inventory inventory);
    Optional<Inventory> getDevice(Long id);

    void deleteDevice(Long id);
    Inventory modifyDevice(Long id, Inventory updatedInventory);
}
