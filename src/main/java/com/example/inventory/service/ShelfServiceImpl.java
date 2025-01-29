package com.example.inventory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.inventory.model.Shelf;
import com.example.inventory.model.ShelfPosition;
import com.example.inventory.model.Inventory;
import com.example.inventory.model.Inventory;
import com.example.inventory.repository.ShelfRepository;
import com.example.inventory.repository.ShelfPositionRepository;
import com.example.inventory.repository.DeviceRepository;

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
    private DeviceRepository deviceRepository;

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
        Optional<Inventory> deviceOptional = deviceRepository.findById(deviceId);
        Optional<ShelfPosition> shelfPositionOptional = shelfPositionRepository.findById(shelfPositionId);

        try {
            if(deviceOptional.isPresent() && shelfPositionOptional.isPresent()) {
                Inventory device = deviceOptional.get();
                ShelfPosition shelfPosition = shelfPositionOptional.get();

                if(device.getShelfPositions() == null) {
                    device.setShelfPositions(new ArrayList<>());
                }

                device.getShelfPositions().add(shelfPosition);

                shelfPosition.setDevice(device);

                deviceRepository.save(device);
                shelfPositionRepository.save(shelfPosition);
            } else {
                throw new RuntimeException("Device or ShelfPosition not found.");
            }
        return Optional.empty();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error adding shelf position to device", e);
            return Optional.empty();
        }
    }

    public Optional<Void> addShelfToShelfPosition(Long shelfId, Long shelfPositionId) {
        Optional<Shelf> shelfOptional = shelfRepository.findById(shelfId);
        Optional<ShelfPosition> shelfPositionOptional = shelfPositionRepository.findById(shelfPositionId);

        try {
            if(shelfOptional.isPresent() && shelfPositionOptional.isPresent()) {
                Shelf s = shelfOptional.get();
                s.setShelfPosition(shelfPositionOptional.get());
                shelfRepository.save(s);
            }
            return Optional.empty();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error adding shelf to shelf position", e);
            return Optional.empty();
        }
    }
    
}
