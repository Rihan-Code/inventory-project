package com.example.inventory.model;

import lombok.*;
import org.springframework.data.neo4j.core.schema.*;
import org.springframework.data.annotation.Id;
import java.util.*;

@Node
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {
    @Id @GeneratedValue
    private Long id;
    private String name;
    private String deviceType;
    public void setId(Long id) {
        this.id = id;
    }

    @Relationship(type = "HAS", direction = Relationship.Direction.OUTGOING)
    private List<ShelfPosition> shelfPositions = new ArrayList<>();

    public List<ShelfPosition> getShelfPositions() {
        return shelfPositions;
    }

    public void setShelfPositions(List<ShelfPosition> shelfPositions) {
        this.shelfPositions = shelfPositions;
    }
}
