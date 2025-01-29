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

    public void setDevice(Inventory device) {
        this.device = device;
    }

    @Relationship(type = "HAS", direction = Relationship.Direction.INCOMING)
    private Inventory device;
}
