package com.bestseller.coffeestore.customer.dto.request;

import com.bestseller.coffeestore.customer.dto.Drink;
import com.bestseller.coffeestore.customer.dto.Topping;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {

    Drink drink;

    List<Topping> toppings;

}
