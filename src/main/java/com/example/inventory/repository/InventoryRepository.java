package com.example.inventory.repository;

import com.example.inventory.model.Inventory;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends Neo4jRepository<Inventory, Long> {
    // automatically provides methods like save, findById, deleteById, etc.
} 
