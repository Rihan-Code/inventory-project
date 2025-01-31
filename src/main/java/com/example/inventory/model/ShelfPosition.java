package com.example.inventory.model;

import lombok.*;
import org.springframework.data.neo4j.core.schema.*;

@Node
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShelfPosition {
    @Id @GeneratedValue
    private Long id;
    private String name;

    public ShelfPosition(long l, String name1) {
        this.id = l;
        this.name = name1;
    }

    @Relationship(type = "HAS", direction = Relationship.Direction.INCOMING)
    private Inventory device;

    @Relationship(type = "HAS", direction = Relationship.Direction.OUTGOING)
    private Shelf shelf;
}
