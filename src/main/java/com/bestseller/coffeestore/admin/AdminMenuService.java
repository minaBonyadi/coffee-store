package com.bestseller.coffeestore.admin;

import com.bestseller.coffeestore.admin.dto.MenuItemRequest;

public interface AdminMenuService {

	void addItem(MenuItemRequest item);

    void updateItem(MenuItemRequest item);

    void deleteItem(MenuItemRequest item);

	void orderReport();
}
