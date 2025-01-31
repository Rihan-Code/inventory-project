package com.example.inventory.model;

import lombok.*;
import org.springframework.data.neo4j.core.schema.*;

@Node
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Shelf {
    @Id @GeneratedValue
    private Long id;
    private String name;
    private String shelfType;

    public Shelf(long l, String name1, String type1) {
        this.id = l;
        this.name = name1;
        this.shelfType = type1;
    }

    // public void setShelfPosition(ShelfPosition shelfPosition);
//    public void setShelfPosition(ShelfPosition shelfPosition) {
//        this.shelfPosition = shelfPosition;
//    }
    // public void deleteShelf(Long id);

     @Relationship(type = "HAS", direction = Relationship.Direction.INCOMING)
     private ShelfPosition shelfPosition;
}
