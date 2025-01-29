package com.example.inventory.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import com.example.inventory.model.ShelfPosition;;

public interface ShelfPositionRepository extends Neo4jRepository<ShelfPosition, Long> {
    
}
