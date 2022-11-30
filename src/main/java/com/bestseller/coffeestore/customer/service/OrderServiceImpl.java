package com.bestseller.coffeestore.customer.service;

import com.bestseller.coffeestore.admin.model.Drink;
import com.bestseller.coffeestore.admin.model.Topping;
import com.bestseller.coffeestore.admin.repository.DrinkRepository;
import com.bestseller.coffeestore.admin.repository.OrderRepository;
import com.bestseller.coffeestore.admin.repository.ToppingRepository;
import com.bestseller.coffeestore.customer.mapper.OrderMapper;
import com.bestseller.coffeestore.customer.discount.DiscountService;
import com.bestseller.coffeestore.customer.dto.request.OrderRequest;
import com.bestseller.coffeestore.customer.dto.request.RegisterOrdersRequest;
import com.bestseller.coffeestore.customer.dto.response.OrderReceiptResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final ToppingRepository toppingRepository;
    private final DrinkRepository drinkRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final DiscountService discountService;

    @Transactional
    @Override
    public OrderReceiptResponse registerOrder(RegisterOrdersRequest registerOrdersRequest) {
        List<Double> amounts =  new ArrayList<>();

        registerOrdersRequest.getOrdersList().forEach(order -> {
            Drink drink = drinkRepository.findById(order.getDrink().getId()).orElseThrow();
            Set<Topping> toppingSet = order.getToppings().stream().map(topping ->
                            toppingRepository.findById(topping.getId()).orElseThrow())
                    .collect(Collectors.toSet());

            orderRepository.save(orderMapper.mapToOrder(drink, toppingSet));

            Double drinkPrice = drink.getPrice();
            Double toppingsPrice = toppingSet.stream().mapToDouble(Topping::getPrice).sum();

            amounts.add(drinkPrice);
            amounts.add(toppingsPrice);
        });

        long drinkCount = registerOrdersRequest.getOrdersList().stream().map(OrderRequest::getDrink).count();

        double totalAmount = amounts.stream().mapToDouble(Double::doubleValue).sum();

        Double finalAmountAfterDiscount = checkDiscountPossibility(amounts.stream().
                        filter(amount-> amount > 0.0).toList(),
                drinkCount, totalAmount);

        return orderMapper.createReceiptResponse(registerOrdersRequest, totalAmount, finalAmountAfterDiscount);
    }


    private Double checkDiscountPossibility(List<Double> amounts, long drinkCount, Double totalAmount) {
        if (totalAmount > 12 && drinkCount >= 3) {
            return discountService.promoteLowestDiscount(totalAmount, amounts);

        } else if (drinkCount >= 3) {
            return discountService.promoteLowerAmountOrderItemDiscount(totalAmount, amounts);

        } else if (totalAmount > 12) {
            return discountService.promoteTwentyFivePercentDiscount(totalAmount);
        }else {
            log.info("discount not included");
            return totalAmount;
        }
    }

}
