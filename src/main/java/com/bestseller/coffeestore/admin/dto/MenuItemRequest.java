package com.bestseller.coffeestore.admin.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
