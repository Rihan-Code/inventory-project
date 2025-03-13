package com.example.inventory.repository;

import com.example.inventory.model.ShelfSummaryDTO;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import com.example.inventory.model.Shelf;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ShelfRepository extends Neo4jRepository<Shelf, Long> {

    @Query(
            "MATCH(s:Shelf)\n" +
            "WHERE ID(s) = $id\n" +
            "OPTIONAL MATCH (s:Shelf)<-[:HAS]-(sp:ShelfPosition)\n" +
            "OPTIONAL MATCH (s)<-[:HAS]-(sp)<-[:HAS]-(d:Device)\n" +
            "    RETURN s.name AS shelfName, s.shelfType AS shelfType, sp.name AS shelfPositionName, " +
            "           ID(sp) AS shelfPositionId, d.name AS deviceName, d.deviceType AS deviceType"
    )
    Optional<List<ShelfSummaryDTO>> shelfSummary(@Param("id") Long id);
}
/*
 * Shelf Name,
 * Shelf Type,
 * Shelf Position Name,
 * Shelf Position Id,
 * Device Name,
 * Device Type
 * */