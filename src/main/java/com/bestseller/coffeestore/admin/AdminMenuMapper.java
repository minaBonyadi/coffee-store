package com.bestseller.coffeestore.admin;

import com.bestseller.coffeestore.admin.dto.MenuItem;
import com.bestseller.coffeestore.admin.model.Drink;
import com.bestseller.coffeestore.admin.model.Topping;

import org.springframework.stereotype.Component;

@Component
public class AdminMenuMapper {

    public Drink toDrink(MenuItem item) {
        Drink drink = new Drink();
        drink.setName(item.getName());
        drink.setPrice(item.getPrice());
        return drink;
    }

    public Topping toTopping(MenuItem item){
        Topping topping = new Topping();
        topping.setName(item.getName());
        topping.setPrice(item.getPrice());
        return topping;
    }

    public Drink mapDrinkMenuToDrink(MenuItem item, Drink drink) {
        drink.setName(item.getName());
        drink.setPrice(item.getPrice());
        return drink;
    }

    public Topping mapToppingMenuToTopping(MenuItem item, Topping topping) {
        topping.setName(item.getName());
        topping.setPrice(item.getPrice());
        return topping;
    }
}
