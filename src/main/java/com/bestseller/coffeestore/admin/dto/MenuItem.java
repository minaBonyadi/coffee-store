package com.bestseller.coffeestore.admin.dto;

import java.math.BigInteger;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class MenuItem {

    private Long id;

    @NotBlank(message = "name should fill")
    private String name;

    @NotNull(message = "price should fill")
	private BigInteger price;

    @NotNull(message = "item type should fill")
    private ItemType itemType;

}
