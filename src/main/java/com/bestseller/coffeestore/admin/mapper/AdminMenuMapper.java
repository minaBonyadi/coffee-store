package com.bestseller.coffeestore.admin.mapper;

import com.bestseller.coffeestore.admin.dto.MenuItemRequest;
import com.bestseller.coffeestore.admin.model.Drink;
import com.bestseller.coffeestore.admin.model.Topping;

import org.springframework.stereotype.Component;

@Component
public class AdminMenuMapper {

    public Drink toDrink(MenuItemRequest item) {
        Drink drink = new Drink();
        drink.setName(item.getName());
        drink.setPrice(item.getPrice());
        return drink;
    }

    public Topping toTopping(MenuItemRequest item){
        Topping topping = new Topping();
        topping.setName(item.getName());
        topping.setPrice(item.getPrice());
        return topping;
    }

    public Drink mapDrinkMenuToDrink(MenuItemRequest item, Drink drink) {
        drink.setName(item.getName());
        drink.setPrice(item.getPrice());
        return drink;
    }

    public Topping mapToppingMenuToTopping(MenuItemRequest item, Topping topping) {
        topping.setName(item.getName());
        topping.setPrice(item.getPrice());
        return topping;
    }
}
