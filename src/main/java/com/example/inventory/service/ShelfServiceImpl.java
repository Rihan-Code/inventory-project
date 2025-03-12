package com.example.inventory.service;

import com.example.inventory.model.Device;
import com.example.inventory.model.ShelfSummaryDTO;
import com.example.inventory.repository.DeviceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.inventory.model.Shelf;
import com.example.inventory.model.ShelfPosition;
import com.example.inventory.repository.ShelfRepository;
import com.example.inventory.repository.ShelfPositionRepository;

import java.util.*;
import java.util.Optional;

@Service
@Slf4j
public class ShelfServiceImpl implements ShelfService {

    private final DeviceRepository deviceRepository;
    private final ShelfPositionRepository shelfPositionRepository;
    private final ShelfRepository shelfRepository;

    // dependency injections
    ShelfServiceImpl(DeviceRepository deviceRepository, ShelfPositionRepository shelfPositionRepository, ShelfRepository shelfRepository) {
        this.shelfPositionRepository = shelfPositionRepository;
        this.deviceRepository = deviceRepository;
        this.shelfRepository = shelfRepository;
    }

    @Override
    public Optional<Shelf> saveShelf(Shelf shelf) {
        return Optional.of(shelfRepository.save(shelf));
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
    public Optional<Shelf> updateShelf(Long id, Shelf updatedShelf) {
        Shelf shelf = shelfRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shelf not found!"));
        shelf.setName(updatedShelf.getName());
        shelf.setShelfType(updatedShelf.getShelfType());
        shelf.setStatus(updatedShelf.getStatus());
        shelfRepository.save(shelf);
        return Optional.of(shelf);
    }

    @Override 
    public Optional<String> deleteShelf(Long id) {
        Shelf shelf = shelfRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Shelf not found"));
        shelf.setStatus("Decommissioned");
        shelfRepository.save(shelf);
        return Optional.of("Shelf soft deleted");
    }

    @Override
    public Optional<ShelfSummaryDTO> getShelfSummaryById(Long id) {
        return shelfRepository.shelfSummary(id);
    }


    // **Shelf Position Requests**
    @Override
    public Optional<ShelfPosition> saveShelfPosition(ShelfPosition shelfPosition) {
        return Optional.of(shelfPositionRepository.save(shelfPosition));
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
    public Optional<ShelfPosition> updateShelfPosition(Long id, ShelfPosition updatedShelfPosition) {
        ShelfPosition shelfPosition = shelfPositionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shelf Position not found!"));
        shelfPosition.setName(updatedShelfPosition.getName());
        shelfPosition.setStatus(updatedShelfPosition.getStatus());
        shelfPositionRepository.save(shelfPosition);
        return Optional.of(shelfPosition);
    }

    @Override
    public Optional<String> deleteShelfPosition(Long id) {
        ShelfPosition shelfPosition = shelfPositionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shelf Position not found"));
        shelfPosition.setStatus("Decommissioned");
        shelfPositionRepository.save(shelfPosition);
        return Optional.of("Shelf Position soft deleted");
    }

    @Transactional
    @Override
    public ResponseEntity<Void> addShelfPositionToDevice(Long deviceId, Long shelfPositionId) {
        if (deviceId == null || shelfPositionId == null) {
            log.error("Device id and Shelf Position id must not be null.");
            throw new IllegalArgumentException("Device id and Shelf Position id must not be null.");
        }
        try {
            // Fetch the device and shelf position from repositories
            Device device = deviceRepository.findById(deviceId)
                    .orElseThrow(() -> new RuntimeException("Device not found with id " + deviceId));
            ShelfPosition shelfPosition = shelfPositionRepository.findById(shelfPositionId)
                    .orElseThrow(() -> new RuntimeException("ShelfPosition not found with id " + shelfPositionId));

            // Check if the shelf position is already assigned to a device
            if (shelfPosition.getDevice() != null) {
                log.error("This shelf position is already assigned to a device.");
                throw new RuntimeException("This shelf position already has a device assigned.");
            }

            // Initialize the set of shelf positions if it's null
            if (device.getShelfPositions() == null) {
                device.setShelfPositions(new HashSet<>());
            }

            // Add the shelf position to the device and set the bidirectional relationship
            device.getShelfPositions().add(shelfPosition);
            shelfPosition.setDevice(device);
            // Save the updated entities
            deviceRepository.save(device);
            shelfPositionRepository.save(shelfPosition);

            log.info("Device with id {} is associated with ShelfPosition id {}", deviceId, shelfPositionId);

            // Return a successful response
            return ResponseEntity.ok().build();

        } catch (IllegalStateException e) {
            log.error("Failed relationship validation while associating device id {} with shelf position id {}: {}",
                    deviceId, shelfPositionId, e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();

        } catch (RuntimeException e) {
            log.error("Unexpected error occurred while associating device id {} with shelf position id {}: {}",
                    deviceId, shelfPositionId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Transactional
    @Override
    public ResponseEntity<Void> addShelfToShelfPosition(Long shelfId, Long shelfPositionId) {
        if (shelfId == null || shelfPositionId == null) {
            log.error("Shelf ID and Shelf Position ID must not be null.");
            throw new IllegalArgumentException("Shelf ID and Shelf Position ID must not be null.");
        }

        try {
            Shelf shelf = shelfRepository.findById(shelfId)
                    .orElseThrow(() -> new RuntimeException("Shelf not found with id " + shelfId));
            ShelfPosition shelfPosition = shelfPositionRepository.findById(shelfPositionId)
                    .orElseThrow(() -> new RuntimeException("ShelfPosition not found with id " + shelfPositionId));


            if (shelfPosition.getShelf() != null) {
                log.error("This shelf position already has a shelf assigned.");
                throw new IllegalStateException("This shelf position already has a shelf assigned.");
            }
            if (shelf.getShelfPosition() != null) {
                log.error("This shelf is already assigned to a shelf position.");
                throw new IllegalStateException("This shelf is already assigned to a shelf position.");
            }

            // saving the shelf instance to shelf position
            shelfPosition.setShelf(shelf);

            // saving the shelf position instance to shelf
            shelf.setShelfPosition(shelfPosition);

            // repository interaction
            shelfPositionRepository.save(shelfPosition);
            shelfRepository.save(shelf);

            log.info("The shelf with id: {} is associated with shelf position id: {}", shelfId, shelfPositionId);

            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            log.error("Relationship validation failed while associating shelf id {} with shelf position id {}: {}", shelfId, shelfPositionId, e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (RuntimeException e) {
            log.error("Unexpected error occurred while associating shelf id {} with shelf position id {}: {}", shelfId, shelfPositionId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
