package com.bestseller.coffeestore.admin.dto;

public enum ItemType {
    DRINK(0), TOPPING(1);

	private Integer value;

	ItemType(Integer value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
