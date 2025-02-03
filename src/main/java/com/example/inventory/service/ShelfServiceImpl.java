package com.example.inventory.service;

import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.inventory.model.Shelf;
import com.example.inventory.model.ShelfPosition;
import com.example.inventory.model.Inventory;
import com.example.inventory.repository.ShelfRepository;
import com.example.inventory.repository.ShelfPositionRepository;
import com.example.inventory.repository.InventoryRepository;

import java.util.*;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ShelfServiceImpl implements ShelfService {

    private static final Logger logger = LoggerFactory.getLogger(ShelfServiceImpl.class);

    @Autowired
    private ShelfRepository shelfRepository;

    @Autowired
    private ShelfPositionRepository shelfPositionRepository;

    @Autowired
    private InventoryRepository deviceRepository;

    @Override
    public Shelf saveShelf(Shelf shelf) {
        return shelfRepository.save(shelf);
    }

    @Override 
    public void deleteShelf(Long id) {
        shelfRepository.deleteById(id);
    }

    @Override
    public Optional<Shelf> getShelf(Long id) {
        return shelfRepository.findById(id);
    }

    @Override
    public Optional<List<Shelf>> getAllShelves() {
        List<Shelf> shelves = shelfRepository.findAll();
        return shelves.isEmpty() ? Optional.empty() : Optional.of(shelves);
    }

    @Override
    public ShelfPosition saveShelfPosition(ShelfPosition shelfPosition) {
        return shelfPositionRepository.save(shelfPosition);
    }

    @Override
    public Optional<ShelfPosition> getShelfPosition(Long id) {
        return shelfPositionRepository.findById(id);
    }

    @Override
    public Optional<List<ShelfPosition>> getAllShelfPositions() {
        List<ShelfPosition> shelfPositions = shelfPositionRepository.findAll();
        return shelfPositions.isEmpty() ? Optional.empty() : Optional.of(shelfPositions);
    }

    @Transactional
    @Override
    public ResponseEntity<Void> addShelfPositionToDevice(Long deviceId, Long shelfPositionId) {
        if (deviceId == null || shelfPositionId == null) {
            throw new IllegalArgumentException("Device id and Shelf Position id must not be null.");
        }
        try {
            Inventory device = deviceRepository.findById(deviceId)
                    .orElseThrow(() -> new RuntimeException("Device not found with id " + deviceId));
            ShelfPosition shelfPosition = shelfPositionRepository.findById(shelfPositionId)
                    .orElseThrow(() -> new RuntimeException("ShelfPosition not found with id " + shelfPositionId));

            // checking if the shelf position already has a device assigned
            if (shelfPosition.getDevice() != null) {
                throw new RuntimeException("This shelf position already has a device assigned.");
            }

            // Initializing the list of shelf positions if null
            if (device.getShelfPositions() == null) {
                device.setShelfPositions(new ArrayList<>());
            }

            // saving shelf position id to the device arraylist
            device.getShelfPositions().add(shelfPositionId);

            // saving the device instance to shelfposition
            shelfPosition.setDevice(device);

            // repository interaction
            deviceRepository.save(device);
            shelfPositionRepository.save(shelfPosition);

            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            logger.error("Relationship validation failed while associating device id {} with shelf positoion id {}: {}", deviceId, shelfPositionId, e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (RuntimeException e) {
            logger.error("Unexpected error occurred while associating device id {} with shelf position id {}: {}", deviceId, shelfPositionId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Transactional
    @Override
    public ResponseEntity<Void> addShelfToShelfPosition(Long shelfId, Long shelfPositionId) {
        if (shelfId == null || shelfPositionId == null) {
            throw new IllegalArgumentException("Shelf ID and Shelf Position ID must not be null.");
        }

        try {
            Shelf shelf = shelfRepository.findById(shelfId)
                    .orElseThrow(() -> new RuntimeException("Shelf not found with id " + shelfId));
            ShelfPosition shelfPosition = shelfPositionRepository.findById(shelfPositionId)
                    .orElseThrow(() -> new RuntimeException("ShelfPosition not found with id " + shelfPositionId));


            if (shelfPosition.getShelf() != null) {
                throw new IllegalStateException("This shelf position already has a shelf assigned.");
            }
            if (shelf.getShelfPosition() != null) {
                throw new IllegalStateException("This shelf is already assigned to a shelf position.");
            }

            // saving the shelf instance to shelf position
            shelfPosition.setShelf(shelf);

            // saving the shelf position instance to shelf
            shelf.setShelfPosition(shelfPosition);

            // repository interaction
            shelfPositionRepository.save(shelfPosition);
            shelfRepository.save(shelf);

            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            logger.error("Relationship validation failed while associating shelf id {} with shelf position id {}: {}", shelfId, shelfPositionId, e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (RuntimeException e) {
            logger.error("Unexpected error occurred while associating shelf id {} with shelf psoition id {}: {}", shelfId, shelfPositionId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
