package com.example.inventory.vo;

import lombok.*;

@Data
public class ShelfVO {
    private Long id;
    private String name;
    private String shelfType;
    private Long shelfPositionId;
}
