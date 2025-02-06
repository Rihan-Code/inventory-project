package com.example.inventory.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.springframework.data.neo4j.core.schema.*;

@Node
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // Avoid cyclic recursion in equals/hashCode.
public class Shelf {
    @Id @GeneratedValue
    private Long id;
    private String name;
    private String shelfType;
    private String status = "Live";

    public Shelf(long l, String name1, String type1, String status1) {
        this.id = l;
        this.name = name1;
        this.shelfType = type1;
        this.status = status1.equalsIgnoreCase("Decommissioned") ? "Decommissioned" : "Live";
    }

//    public void setShelfPosition(ShelfPosition shelfPosition) {
//        this.shelfPosition = shelfPosition;
//    }

    @JsonBackReference // Handle serialization for bidirectional reference
    @ToString.Exclude // Exclude from Lombok's toString to avoid infinite recursion
    @Relationship(type = "HAS", direction = Relationship.Direction.INCOMING)
    private ShelfPosition shelfPosition;
}
