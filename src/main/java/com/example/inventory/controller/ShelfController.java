package com.example.inventory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.inventory.model.Shelf;
import com.example.inventory.service.ShelfService;
import com.example.inventory.model.ShelfPosition;
import org.springframework.http.ResponseEntity;

import java.lang.*;
import java.util.*;

@RestController
@RequestMapping("/api/shelves")
public class ShelfController {
    @Autowired
    private ShelfService shelfService;

    // saving shelf in db
    @PostMapping
    public ResponseEntity<Shelf> saveShelf(@RequestBody Shelf shelf) {
        Shelf savedShelf = shelfService.saveShelf(shelf);
        return ResponseEntity.ok(savedShelf);
    }

    // getting shelf by id
    @GetMapping("/{id}")
    public ResponseEntity<Shelf> getShelf(@PathVariable Long id) {
        return shelfService.getShelf(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // getting list of all the shelves
    @GetMapping
    public ResponseEntity<List<Shelf>> getAllShelves() {
        return shelfService.getAllShelves()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // deleting a shelf using it's id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShelf(@PathVariable Long id) {
        shelfService.deleteShelf(id); // do not hard delete
        return ResponseEntity.noContent().build();
    }

    // saving shelf positions
    @PostMapping("/positions")
    public ResponseEntity<ShelfPosition> saveShelfPosition(@RequestBody ShelfPosition shelfPosition) {
        ShelfPosition savedShelfPosition = shelfService.saveShelfPosition(shelfPosition);
        return ResponseEntity.ok(savedShelfPosition);
    }

    // retrieving shelf position
    @GetMapping("/positions/{id}")
    public ResponseEntity<ShelfPosition> getShelfPosition(@PathVariable Long id) {
        return shelfService.getShelfPosition(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // getting all the shelf positions
    @GetMapping("/positions")
    public ResponseEntity<List<ShelfPosition>> getAllShelfPositions() {
        return shelfService.getAllShelfPositions()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // adding shlef position to device
    @PostMapping("/{deviceId}/add-shelf-position/{shelfPositionId}")
    public void addShelfPositionToDevice(@PathVariable Long deviceId, @PathVariable Long shelfPositionId) {
        shelfService.addShelfPositionToDevice(deviceId, shelfPositionId);
    }

    // adding shelf to shelf position
    @PostMapping("/{shelfPositionId}/add-shelf/{shelfId}")
    public void addShelfToShelfPosition(@PathVariable Long shelfPositionId, @PathVariable Long shelfId) {
        shelfService.addShelfToShelfPosition(shelfId, shelfPositionId);
    }
}
