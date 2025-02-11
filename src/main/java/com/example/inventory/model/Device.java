package com.example.inventory.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Property("deviceId")
    private Long deviceId;
    private String name;
    private String deviceType;
    private String status = "Live";

    @JsonIgnore // prevents serialization issues, otherwise a circular references or serialization issues when converting *JASON to Java objects using Jackson in Spring Boot*
    @JsonManagedReference // Handle serialization for bidirectional reference, marks the forward relationship
    @ToString.Exclude // Exclude from Lombok's toString to avoid infinite recursion
    @Relationship(type = "HAS", direction = Relationship.Direction.OUTGOING)
    private Set<ShelfPosition> shelfPositions = new HashSet<>();


    public Device(Long deviceId, String name, String deviceType, String status, Set<ShelfPosition> shelfPositions) {
        this.deviceId = deviceId;
        this.name = name;
        this.deviceType = deviceType;
        this.status = status.equalsIgnoreCase("Decommissioned") ? "Decommissioned" : "Live";
        this.shelfPositions = shelfPositions;
    }
}