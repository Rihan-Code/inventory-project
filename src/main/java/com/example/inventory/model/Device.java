package com.example.inventory.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.data.neo4j.core.schema.*;

import java.util.HashSet;
import java.util.Set;

@Node
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // Avoid cyclic recursion in equals/hashCode.
public class Device {
    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;

    private String name;
    private String deviceType;
    private String status = "Live";

    @JsonManagedReference // Handle serialization for bidirectional reference
    @ToString.Exclude // Exclude from Lombok's toString to avoid infinite recursion
    @Relationship(type = "HAS", direction = Relationship.Direction.OUTGOING)
    private Set<ShelfPosition> shelfPositions = new HashSet<>();

    // Custom Constructor
    public Device(Long id, String name, String deviceType, String status) {
        this.id = id;
        this.name = name;
        this.deviceType = deviceType;
        this.status = status.equalsIgnoreCase("Decommissioned") ? "Decommissioned" : "Live";
    }
}