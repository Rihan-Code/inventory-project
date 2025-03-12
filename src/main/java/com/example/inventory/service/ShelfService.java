package com.example.inventory.service;

import com.example.inventory.model.Shelf;
import com.example.inventory.model.ShelfPosition;
import com.example.inventory.model.ShelfSummaryDTO;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;

import java.util.*;

public interface ShelfService {
    Optional<Shelf> saveShelf(Shelf shelf);
    Optional<Shelf> getShelf(Long id);
    Optional<List<Shelf>> getAllShelves();
    Optional<String> deleteShelf(Long id);
    Optional<Shelf> updateShelf(Long id, Shelf updatedShelf);
    Optional<ShelfSummaryDTO> getShelfSummaryById(Long id);


    Optional<ShelfPosition> saveShelfPosition(ShelfPosition shelfPosition);
    Optional<ShelfPosition> getShelfPosition(Long id);
    Optional<List<ShelfPosition>> getAllShelfPositions();
    Optional<ShelfPosition> updateShelfPosition(Long id, ShelfPosition updatedShelfPosition);
    Optional<String> deleteShelfPosition(Long id);

    ResponseEntity<Void> addShelfPositionToDevice(Long deviceId, Long shelfPositionId);
    ResponseEntity<Void> addShelfToShelfPosition(Long shelfId, Long shelfPositionId);
}
