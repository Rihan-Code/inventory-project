package com.example.inventory.service;

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

    @Override
    public Optional<Void> addShelfPositionToDevice(Long deviceId, Long shelfPositionId) {
        Optional<Inventory> deviceOptional = Optional.ofNullable(deviceRepository.findById(deviceId)
                .orElseThrow(() -> new RuntimeException("Device not found with id " + deviceId)));

        Optional<ShelfPosition> shelfPositionOptional = Optional.ofNullable(shelfPositionRepository.findById(shelfPositionId))
                .orElseThrow(() -> new RuntimeException("ShelfPosition not found with id " + shelfPositionId));

        try {
            if(deviceOptional.isPresent() && shelfPositionOptional.isPresent()) {
                Inventory device = deviceOptional.get();
                ShelfPosition shelfPosition = shelfPositionOptional.get();

                if(device.getShelfPositions() == null) {
                    device.setShelfPositions(new ArrayList<>());
                }

                device.getShelfPositions().add(shelfPosition);

                deviceRepository.save(device);
                shelfPositionRepository.save(shelfPosition);
            } else {
                throw new RuntimeException("Device or ShelfPosition not found.");
            }
        return Optional.empty();
        } catch (Exception e) {
            logger.error("Error adding shelf position to device", e);
            return Optional.empty();
        }
    }

    public Optional<Void> addShelfToShelfPosition(Long shelfId, Long shelfPositionId) {
        Shelf shelf = shelfRepository.findById(shelfId)
                .orElseThrow(() -> new RuntimeException("Shelf not found with id " + shelfId));
        ShelfPosition shelfPosition = shelfPositionRepository.findById(shelfPositionId)
                .orElseThrow(() -> new RuntimeException("ShelfPosition not found with id " + shelfPositionId));

        try {
            // Validating if the one-to-one relationship is already established
            if (shelfPosition.getShelf() != null) {
                throw new RuntimeException("This shelf position already has a shelf assigned.");
            }
            if (shelf.getShelfPosition() != null) {
                throw new RuntimeException("This shelf is already assigned to a shelf position.");
            }

            shelfPosition.setShelf(shelf);
            shelf.setShelfPosition(shelfPosition);

            shelfPositionRepository.save(shelfPosition);
            shelfRepository.save(shelf);

            return Optional.empty();
        } catch (Exception e) {
            logger.error("Error occurred while saving shelfPosition with shelf.", e.getCause());
            throw new RuntimeException("Failed to save Shelf to ShelfPosition", e);
        }
    }
}
