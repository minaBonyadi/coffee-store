package com.bestseller.coffeestore.admin.service;

import com.bestseller.coffeestore.admin.dto.MenuItemRequest;
import com.bestseller.coffeestore.admin.dto.OrderReportResponse;
import com.bestseller.coffeestore.exception.DrinkNotFoundException;
import com.bestseller.coffeestore.exception.ToppingNotFoundException;

public interface AdminMenuService {

	void addItem(MenuItemRequest item);

    void updateItem(MenuItemRequest item) throws DrinkNotFoundException, ToppingNotFoundException;

    void deleteItem(MenuItemRequest item) throws DrinkNotFoundException, ToppingNotFoundException;

	OrderReportResponse orderReport();
}
