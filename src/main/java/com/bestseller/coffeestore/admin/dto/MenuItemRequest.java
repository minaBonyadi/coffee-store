package com.bestseller.coffeestore.admin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MenuItemRequest {

    private Long id;

    @NotBlank(message = "name should fill")
    private String name;

    @NotNull(message = "price should fill")
	private Double price;

    @NotNull(message = "item type should fill")
    private ItemType itemType;

}
