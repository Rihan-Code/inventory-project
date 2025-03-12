package com.example.inventory.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShelfSummaryDTO {
    private String shelfName;
    private String shelfType;
    private String shelfPositionName;
    private Long shelfPositionId;
    private String deviceName;
    private String deviceType;
}

/*
 * Shelf Name,
 * Shelf Type,
 * Shelf Position Name,
 * Shelf Position Id,
 * Device Name,
 * Device Type
 * */
