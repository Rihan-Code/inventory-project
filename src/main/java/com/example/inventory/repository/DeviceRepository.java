package com.example.inventory.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import com.example.inventory.model.Inventory;

public interface DeviceRepository extends Neo4jRepository<Inventory, Long> {
    
}