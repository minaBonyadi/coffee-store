package com.bestseller.coffeestore.customer.dto.request;

import com.bestseller.coffeestore.customer.dto.Drink;
import com.bestseller.coffeestore.customer.dto.Topping;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderRequest {
    String trackingCode;

    Drink drink;

    List<Topping> toppings;

}
