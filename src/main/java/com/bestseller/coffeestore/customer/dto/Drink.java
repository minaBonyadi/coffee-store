package com.bestseller.coffeestore.customer.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Drink {
    Long id;

    String name;

    Double price;
}
