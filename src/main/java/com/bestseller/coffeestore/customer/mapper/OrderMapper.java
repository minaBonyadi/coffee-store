package com.bestseller.coffeestore.customer.mapper;

import com.bestseller.coffeestore.admin.model.Drink;
import com.bestseller.coffeestore.admin.model.Orders;
import com.bestseller.coffeestore.admin.model.Topping;
import com.bestseller.coffeestore.customer.dto.request.RegisterOrdersRequest;
import com.bestseller.coffeestore.customer.dto.response.OrderReceiptResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    public Orders mapToOrder(Drink drink, Set<Topping> toppings) {
        Orders orders = new Orders();
        orders.setDrink(drink);
        orders.setToppings(toppings);

        return orders;
    }

    public OrderReceiptResponse createReceiptResponse(RegisterOrdersRequest registerOrdersRequest,
                                                     double totalAmount, double finalAmountAfterDiscount) {
        OrderReceiptResponse response = new OrderReceiptResponse();

        if (totalAmount == finalAmountAfterDiscount) {
            response.setDescription("discount not included");
        }else {
            response.setDescription("discount included");
        }
        response.setTotalAmount(totalAmount);
        response.setAmountAfterDiscount(response.getTotalAmount() - finalAmountAfterDiscount);
        response.setPayableAmount(finalAmountAfterDiscount);
        response.setOrderRequests(registerOrdersRequest.getOrdersList());
        return response;
    }
}
