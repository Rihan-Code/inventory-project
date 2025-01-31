package com.example.inventory.service;

import com.example.inventory.model.Shelf;
import com.example.inventory.model.ShelfPosition;
import org.springframework.http.ResponseEntity;

import java.util.*;

public interface ShelfService {
    Shelf saveShelf(Shelf shelf);
    Optional<Shelf> getShelf(Long id);
    Optional<List<Shelf>> getAllShelves();
    void deleteShelf(Long id);

    ShelfPosition saveShelfPosition(ShelfPosition shelfPosition);
    Optional<ShelfPosition> getShelfPosition(Long id);
    Optional<List<ShelfPosition>> getAllShelfPositions();

    ResponseEntity<Void> addShelfPositionToDevice(Long deviceId, Long shelfPositionId);
    ResponseEntity<Void> addShelfToShelfPosition(Long shelfId, Long shelfPositionId);
}
