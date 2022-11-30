package com.bestseller.coffeestore.customer.service;

import com.bestseller.coffeestore.admin.model.Drink;
import com.bestseller.coffeestore.admin.model.Orders;
import com.bestseller.coffeestore.admin.model.Topping;
import com.bestseller.coffeestore.admin.repository.DrinkRepository;
import com.bestseller.coffeestore.admin.repository.OrderRepository;
import com.bestseller.coffeestore.admin.repository.ToppingRepository;
import com.bestseller.coffeestore.customer.discount.DiscountService;
import com.bestseller.coffeestore.customer.dto.request.OrderRequest;
import com.bestseller.coffeestore.customer.dto.request.RegisterOrdersRequest;
import com.bestseller.coffeestore.customer.dto.response.OrderReceiptResponse;
import com.bestseller.coffeestore.customer.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
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

    /**
     *
     * @param registerOrdersRequest customer order request including list of drinks and their toppings
     * @return customer order receipt
     */
    @Transactional
    @Override
    public OrderReceiptResponse registerOrder(RegisterOrdersRequest registerOrdersRequest) {
        log.info("gonna register order");
        List<Double> amounts =  new ArrayList<>();
        registerOrdersRequest.getOrdersList().forEach(order -> {
            Drink drink = drinkRepository.findById(order.getDrink().getId()).orElse(new Drink());

            var toppings = order.getToppings().stream().map(topping ->
                            toppingRepository.findById(topping.getId()).orElse(new Topping()))
                    .collect(Collectors.toList());

            Orders orders = orderRepository.save(orderMapper.mapToOrder(drink, toppings));
            log.info("save coming order to storage by orderId {}", orders.getId());

            Double drinkPrice = drink.getPrice();
            Double toppingsPrice = toppings.stream().mapToDouble(Topping::getPrice).sum();

            amounts.add(drinkPrice);
            amounts.add(toppingsPrice);
        });

        long drinkCount = registerOrdersRequest.getOrdersList().stream().map(OrderRequest::getDrink).count();

        double totalAmount = amounts.stream().mapToDouble(Double::doubleValue).sum();

        Double payableAmount = getTotalAmountAfterDiscountChecked(amounts.stream().
                        filter(amount-> amount > 0.0).toList(),
                drinkCount, totalAmount);

        log.info("receipt for total amount {}, payable amount {} going to create", totalAmount, payableAmount);
        return orderMapper.createReceiptResponse(registerOrdersRequest, totalAmount, payableAmount);
    }

    /**
     *
     * @param amounts list of order items prices
     * @param drinkCount drinks count in order list
     * @param totalAmount  total amount of an order items
     * @return payable amount after discount checked
     */
    private Double getTotalAmountAfterDiscountChecked(List<Double> amounts, long drinkCount, Double totalAmount) {
        if (totalAmount > 12 && drinkCount >= 3) {
            log.info("discount promotion for more than twelve amount and drink items more than three," +
                    " is possible for total amount {}", totalAmount);
            return discountService.getLowestDiscount(totalAmount, amounts);

        } else if (drinkCount >= 3) {
            log.info("discount promotion for mora than three drink items," +
                    " is possible for total amount {}", totalAmount);
            return discountService.getLowerAmountOrderItemDiscount(totalAmount, amounts);

        } else if (totalAmount > 12) {
            log.info("discount promotion more than twelve is possible for total amount {}", totalAmount);
            return discountService.promoteTwentyFivePercentDiscount(totalAmount);
        } else {
            return totalAmount;
        }
    }

}
