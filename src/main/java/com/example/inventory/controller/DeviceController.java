package com.example.inventory.controller;

import com.example.inventory.model.Device;
import com.example.inventory.service.DeviceService;
import com.example.inventory.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.lang.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    private final DeviceService deviceService;
    DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping
    public ResponseEntity<Optional<Device>> saveDevice(@RequestBody Device device) {
        Optional<Device> savedDevice = deviceService.saveDevice(device);
        return ResponseEntity.ok(savedDevice);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Device> getDevice(@PathVariable Long id) {
        return deviceService.getDevice(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Device> modifyDevice(@PathVariable long id, @RequestBody Device updatedDevice) {
        return ResponseEntity.ok(deviceService.modifyDevice(id, updatedDevice));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable Long id) {
        deviceService.deleteDevice(id);
        return ResponseEntity.noContent().build();
    }
}
