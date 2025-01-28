package com.example.inventory.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {
    @Id
    private Long id;
    private String name;
    private String deviceType;
    public void setId(Long id) {
        this.id = id;
    }
}
