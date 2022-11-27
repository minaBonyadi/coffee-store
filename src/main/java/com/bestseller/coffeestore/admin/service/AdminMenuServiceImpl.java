package com.bestseller.coffeestore.admin.service;


import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;

import com.bestseller.coffeestore.admin.AdminMenuMapper;
import com.bestseller.coffeestore.admin.AdminMenuService;
import com.bestseller.coffeestore.admin.dto.ItemType;
import com.bestseller.coffeestore.admin.dto.MenuItemRequest;
import com.bestseller.coffeestore.admin.model.Drink;
import com.bestseller.coffeestore.admin.model.Order;
import com.bestseller.coffeestore.admin.model.Topping;
import com.bestseller.coffeestore.admin.repository.DrinkRepository;
import com.bestseller.coffeestore.admin.repository.OrderRepository;
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

	private final OrderRepository orderRepository;

    @Override
    public void addItem(MenuItemRequest item) {
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
    public void updateItem(MenuItemRequest item) {
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
    public void deleteItem(MenuItemRequest item) {
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

	@Override
	public void orderReport() {
		List<Order> orderList = orderRepository.findAll();

		var results = orderList.stream()
				.collect(Collectors.groupingBy(Order::getDrink)).entrySet().stream()
				.collect(Collectors.toMap(Entry::getKey, entry-> entry.getValue().stream().map(Order::getToppings)
						.flatMap(Collection::stream).toList()));

		mostCommon(results.values().stream().flatMap(Collection::stream).toList());
	}

	private Topping mostCommon(List<Topping> list) {
		Map<Topping, Integer> map = new HashMap<>();

		for (Topping t : list) {
			Integer val = map.get(t);
			map.put(t, val == null ? 1 : val + 1);
		}

		Entry<Topping, Integer> max = null;

		for (Entry<Topping, Integer> e : map.entrySet()) {
			if (max == null || e.getValue() > max.getValue())
				max = e;
		}

		return Objects.requireNonNull(max).getKey();
	}
}
