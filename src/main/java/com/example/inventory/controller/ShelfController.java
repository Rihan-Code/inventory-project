package com.example.inventory.controller;

import com.example.inventory.model.ShelfSummaryDTO;
import com.example.inventory.service.ShelfServiceImpl;
import org.springframework.web.bind.annotation.*;
import com.example.inventory.model.Shelf;
import com.example.inventory.model.ShelfPosition;
import org.springframework.http.ResponseEntity;

import java.lang.*;
import java.util.*;

@RestController
@RequestMapping("/api/shelves")
public class ShelfController {
    final private ShelfServiceImpl shelfServiceImpl;
    ShelfController(ShelfServiceImpl shelfServiceImpl) {
        this.shelfServiceImpl = shelfServiceImpl;
    }

    // saving shelf in db
    @PostMapping
    public ResponseEntity<Optional<Shelf>> saveShelf(@RequestBody Shelf shelf) {
        return ResponseEntity.ok(shelfServiceImpl.saveShelf(shelf));
    }

    // getting shelf by id
    @GetMapping("/{id}")
    public ResponseEntity<Shelf> getShelf(@PathVariable Long id) {
        return shelfServiceImpl.getShelf(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // getting list of all the shelves
    @GetMapping
    public ResponseEntity<List<Shelf>> getAllShelves() {
        return shelfServiceImpl.getAllShelves()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // deleting a shelf using it's id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShelf(@PathVariable Long id) {
        shelfServiceImpl.deleteShelf(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<Shelf>> updateShelf(@PathVariable Long id, @RequestBody Shelf updatedShelf) {
        return ResponseEntity.ok(shelfServiceImpl.updateShelf(id, updatedShelf));
    }

    @GetMapping("/summary/{id}")
    public ResponseEntity<Optional<ShelfSummaryDTO>> shelfSummary(@PathVariable Long id) {
        return ResponseEntity.ok(shelfServiceImpl.getShelfSummaryById(id));
    }


    // **Shelf Positions Requests**
    // saving shelf positions
    @PostMapping("/positions")
    public ResponseEntity<Optional<ShelfPosition>> saveShelfPosition(@RequestBody ShelfPosition shelfPosition) {
        Optional<ShelfPosition> savedShelfPosition = shelfServiceImpl.saveShelfPosition(shelfPosition);
        return ResponseEntity.ok(savedShelfPosition);
    }

    // retrieving shelf position
    @GetMapping("/positions/{id}")
    public ResponseEntity<ShelfPosition> getShelfPosition(@PathVariable Long id) {
        return shelfServiceImpl.getShelfPosition(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // getting all the shelf positions
    @GetMapping("/positions")
    public ResponseEntity<List<ShelfPosition>> getAllShelfPositions() {
        return shelfServiceImpl.getAllShelfPositions()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/positions/{id}")
    public ResponseEntity<Void> deleteShelfPosition(@PathVariable Long id) {
        shelfServiceImpl.deleteShelfPosition(id); // do not hard delete
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/positions/{id}")
    public ResponseEntity<Optional<ShelfPosition>> updateShelfPosition(@PathVariable Long id, @RequestBody ShelfPosition updatedShelfPosition) {
        return ResponseEntity.ok(shelfServiceImpl.updateShelfPosition(id, updatedShelfPosition));
    }

    // adding shlef position to device
    @PostMapping("/{deviceId}/add-shelf-position/{shelfPositionId}")
    public void addShelfPositionToDevice(@PathVariable Long deviceId, @PathVariable Long shelfPositionId) {
        shelfServiceImpl.addShelfPositionToDevice(deviceId, shelfPositionId);
    }

    // adding shelf to shelf position
    @PostMapping("/{shelfPositionId}/add-shelf/{shelfId}")
    public void addShelfToShelfPosition(@PathVariable Long shelfPositionId, @PathVariable Long shelfId) {
        shelfServiceImpl.addShelfToShelfPosition(shelfId, shelfPositionId);
    }
}
