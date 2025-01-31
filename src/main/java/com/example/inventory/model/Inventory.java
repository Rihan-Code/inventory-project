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
    private List<Long> shelfPositions = new ArrayList<>();


    public Inventory(long l, String device1, String type1, Long shelfPositionId) {
        this.id = l;
        this.name = device1;
        this.deviceType = type1;
        this.shelfPositions.add(shelfPositionId);
    }

//    public void setId(Long id) {
//        this.id = id;
//    }

    @Relationship(type = "HAS", direction = Relationship.Direction.OUTGOING)
    private ShelfPosition shelfPosition;
}
