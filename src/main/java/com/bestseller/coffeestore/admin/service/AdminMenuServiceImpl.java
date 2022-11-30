package com.bestseller.coffeestore.admin.service;


import com.bestseller.coffeestore.admin.dto.ItemType;
import com.bestseller.coffeestore.admin.dto.MenuItemRequest;
import com.bestseller.coffeestore.admin.dto.OrderReportResponse;
import com.bestseller.coffeestore.admin.mapper.AdminMenuMapper;
import com.bestseller.coffeestore.admin.model.Drink;
import com.bestseller.coffeestore.admin.model.Orders;
import com.bestseller.coffeestore.admin.model.Topping;
import com.bestseller.coffeestore.admin.repository.DrinkRepository;
import com.bestseller.coffeestore.admin.repository.OrderRepository;
import com.bestseller.coffeestore.admin.repository.ToppingRepository;
import com.bestseller.coffeestore.exception.DrinkNotFoundException;
import com.bestseller.coffeestore.exception.ToppingNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminMenuServiceImpl implements AdminMenuService {

    private final AdminMenuMapper mapper;
    private final DrinkRepository drinkRepository;
    private final ToppingRepository toppingRepository;
	private final OrderRepository orderRepository;

    /**
     *
     * @param item menu item
     */
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

    /**
     *
     * @param item menu item
     * @throws DrinkNotFoundException throw exception if drink not exist
     * @throws ToppingNotFoundException throw exception if topping not exist
     */
    @Override
    public void updateItem(MenuItemRequest item) throws DrinkNotFoundException, ToppingNotFoundException {
		log.info("gonna update a menu item");

        if (item.getItemType().equals(ItemType.DRINK)) {
            Drink drink = drinkRepository.findById(item.getId()).orElseThrow(DrinkNotFoundException::new);
            drinkRepository.save(mapper.mapDrinkMenuToDrink(item, drink));

            log.info("update a drink item in a menu by id {}, name {}", drink.getId(),
                    drink.getName());

        } else if (item.getItemType().equals(ItemType.TOPPING)) {
            Topping topping = toppingRepository.findById(item.getId()).orElseThrow(ToppingNotFoundException::new);
			toppingRepository.save(mapper.mapToppingMenuToTopping(item, topping));
			log.info("update a topping item in a menu by id {}, name {}",
                    topping.getId(), topping.getName());
        }
    }

    /**
     *
     * @param item menu item
     * @throws DrinkNotFoundException throw exception if drink not exist
     * @throws ToppingNotFoundException throw exception if topping not exist
     */
	@Override
    public void deleteItem(MenuItemRequest item) throws DrinkNotFoundException, ToppingNotFoundException {
		log.info("gonna delete a menu item");

		if (item.getItemType().equals(ItemType.DRINK)) {
            Drink drink = drinkRepository.findById(item.getId()).orElseThrow(DrinkNotFoundException::new);
            drinkRepository.delete(drink);
            log.info("delete a drink item in a menu by id {}, name {}", drink.getId(),
                    drink.getName());

        } else if(item.getItemType().equals(ItemType.TOPPING)) {
            Topping topping = toppingRepository.findById(item.getId()).orElseThrow(ToppingNotFoundException::new);
            toppingRepository.delete(topping);
            log.info("delete a topping item in a menu by id {}, name {}", topping.getId(),
                    topping.getName());
        }
    }

    /**
     *
     * @return order report response based on most common topping for a specific drink
     */
    @Transactional
	@Override
	public OrderReportResponse orderReport() {
        log.info("gonna get order reports for admin user");
		List<Orders> ordersList = orderRepository.getOrdersReports();

		var results =
                ordersList.stream().collect(Collectors.toMap(Orders::getDrink, Orders::getToppings));

		Map<Drink, Topping> responses = new HashMap<>();
		results.forEach((drink, toppings) -> {

            List<Topping> repeatedToppings = results.values().stream().flatMap(Collection::stream)
                    .filter(topping -> Collections.frequency(results.values(),
                    topping) > 1).toList();

			if (!repeatedToppings.isEmpty()) {
                responses.put(drink, mostCommon(repeatedToppings));
            }
		});

		OrderReportResponse response = new OrderReportResponse();
		response.setOrderReports(responses);

        log.info("gonna send order reports for admin user");
		return response;
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
