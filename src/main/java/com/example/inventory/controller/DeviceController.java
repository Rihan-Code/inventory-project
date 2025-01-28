package com.example.inventory.controller;

import com.example.inventory.service.InventoryService;
import com.example.inventory.model.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.lang.*;;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {
    @Autowired
    private InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<Inventory> saveDevice(@RequestBody Inventory inventory) {
        Inventory savedDevice = inventoryService.saveDevice(inventory);
        return ResponseEntity.ok(savedDevice);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getDevice(@PathVariable Long id) {
        return inventoryService.getDevice(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Inventory> modifyDevice(@PathVariable long id, @RequestBody Inventory updatedInventory) {
        return ResponseEntity.ok(inventoryService.modifyDevice(id, updatedInventory));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable Long id) {
        inventoryService.deleteDevice(id);
        return ResponseEntity.noContent().build();
    }
}
