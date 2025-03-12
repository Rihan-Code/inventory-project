package com.example.inventory.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.springframework.data.neo4j.core.schema.*;

@Node
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // Avoid cyclic recursion in equals/hashCode.
public class ShelfPosition {
    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include // Use only the ID for equality and hashing.
    private Long id;
    private String name;
    private String status = "Live";


//    @JsonBackReference // Handle serialization for bidirectional reference, prevents infinite recursion
    @Relationship(type = "HAS", direction = Relationship.Direction.INCOMING)
    @ToString.Exclude // Exclude from Lombok's toString to avoid infinite recursion
    private Device device;

    @JsonBackReference // Handle serialization for bidirectional reference
    @ToString.Exclude // Exclude from Lombok's toString to avoid infinite recursion
    @Relationship(type = "HAS", direction = Relationship.Direction.OUTGOING)
    private Shelf shelf;

    // Custom Constructor
    public ShelfPosition(Long id, String name, String status) {
        this.id = id;
        this.name = name;
        this.status = status.equalsIgnoreCase("Decommissioned") ? "Decommissioned" : "Live";
    }
}