package com.bestseller.coffeestore.admin.mapper;

import com.bestseller.coffeestore.admin.dto.MenuItemRequest;
import com.bestseller.coffeestore.admin.model.Drink;
import com.bestseller.coffeestore.admin.model.Topping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AdminMenuMapper {

    @Mapping(target = "name", source = "item.name")
    @Mapping(target = "price", source = "item.price")
    Drink toDrink(MenuItemRequest item);

    @Mapping(target = "name", source = "item.name")
    @Mapping(target = "price", source = "item.price")
    Topping toTopping(MenuItemRequest item);

    default Drink mapDrinkMenuToDrink(MenuItemRequest item, Drink drink) {
        drink.setName(item.getName());
        drink.setPrice(item.getPrice());
        return drink;
    }

    default Topping mapToppingMenuToTopping(MenuItemRequest item, Topping topping) {
        topping.setName(item.getName());
        topping.setPrice(item.getPrice());
        return topping;
    }
}
