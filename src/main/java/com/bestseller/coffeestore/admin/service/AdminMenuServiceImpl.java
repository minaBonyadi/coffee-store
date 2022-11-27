package com.bestseller.coffeestore.admin.service;


import com.bestseller.coffeestore.admin.AdminMenuMapper;
import com.bestseller.coffeestore.admin.AdminMenuService;
import com.bestseller.coffeestore.admin.dto.ItemType;
import com.bestseller.coffeestore.admin.dto.MenuItem;
import com.bestseller.coffeestore.admin.model.Drink;
import com.bestseller.coffeestore.admin.model.Topping;
import com.bestseller.coffeestore.admin.repository.DrinkRepository;
import com.bestseller.coffeestore.admin.repository.ToppingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminMenuServiceImpl implements AdminMenuService {

    private final AdminMenuMapper mapper;
    private final DrinkRepository drinkRepository;
    private final ToppingRepository toppingRepository;

    @Override
    public void addItem(MenuItem item) {
        log.info("gonna add an item to a menu");
        if (item.getItemType().equals(ItemType.DRINK)) {
            Drink drink = mapper.toDrink(item);
            drinkRepository.save(drink);
            log.info("add a drink item to a menu by name {}", item.getName());

        } else if (item.getItemType().equals(ItemType.TOPPING)) {
            Topping topping = mapper.toTopping(item);
            toppingRepository.save(topping);
            log.info("add a topping item to a menu by name {}", item.getName());
        }
    }

    @Override
    public void updateItem(MenuItem item) {
		log.info("gonna update a menu item");

        if (item.getItemType().equals(ItemType.DRINK)) {
            Drink drink = drinkRepository.findById(item.getId()).orElseThrow();
            drinkRepository.save(mapper.mapDrinkMenuToDrink(item, drink));

            log.info("update a drink item in a menu by id {}, name {}", drink.getId(),
                    drink.getName());

        } else if (item.getItemType().equals(ItemType.TOPPING)) {
            Topping topping = toppingRepository.findById(item.getId()).orElseThrow();
			toppingRepository.save(mapper.mapToppingMenuToTopping(item, topping));
			log.info("update a topping item in a menu by id {}, name {}",
                    topping.getId(), topping.getName());
        }
    }
	@Override
    public void deleteItem(MenuItem item) {
		log.info("gonna delete a menu item");

		if (item.getItemType().equals(ItemType.DRINK)) {
            Drink drink = drinkRepository.findById(item.getId()).orElseThrow();
            drinkRepository.delete(drink);
            log.info("delete a drink item in a menu by id {}, name {}", drink.getId(),
                    drink.getName());

        } else if(item.getItemType().equals(ItemType.TOPPING)) {
            Topping topping = toppingRepository.findById(item.getId()).orElseThrow();
            toppingRepository.delete(topping);
            log.info("delete a topping item in a menu by id {}, name {}", topping.getId(),
                    topping.getName());
        }
    }

}
