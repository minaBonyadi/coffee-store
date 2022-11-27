package com.bestseller.coffeestore.admin;

import com.bestseller.coffeestore.admin.dto.MenuItem;

public interface AdminMenuService {

	void addItem(MenuItem item);

    void updateItem(MenuItem item);

    void deleteItem(MenuItem item);

}
