package com.example.inventory.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import com.example.inventory.model.Shelf;

public interface ShelfRepository extends Neo4jRepository<Shelf, Long> {
    
}
