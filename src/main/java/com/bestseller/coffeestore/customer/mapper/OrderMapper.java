package com.bestseller.coffeestore.customer.mapper;

import com.bestseller.coffeestore.admin.model.Drink;
import com.bestseller.coffeestore.admin.model.Orders;
import com.bestseller.coffeestore.admin.model.Topping;
import com.bestseller.coffeestore.customer.dto.request.OrderRequest;
import com.bestseller.coffeestore.customer.dto.request.RegisterOrdersRequest;
import com.bestseller.coffeestore.customer.dto.response.OrderReceiptResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    /**
     *
     * @param drink drink
     * @param toppings list of toppings
     * @return orders
     */
    public Orders mapToOrder(Drink drink, List<Topping> toppings) {
        Orders orders = new Orders();
        orders.setDrink(drink);
        orders.setToppings(toppings);

        return orders;
    }

    /**
     *
     * @param registerOrdersRequest register orders request
     * @param totalAmount total amount
     * @param payableAmount payable amount
     * @return order receipt response
     */
    public OrderReceiptResponse createReceiptResponse(RegisterOrdersRequest registerOrdersRequest,
                                                     double totalAmount, double payableAmount) {
        OrderReceiptResponse response = new OrderReceiptResponse();

        if (totalAmount == payableAmount) {
            response.setDescription("discount not included");
        } else {
            response.setDescription("discount included");
        }
        response.setTotalAmount(totalAmount);
        response.setAmountAfterDiscount(response.getTotalAmount() - payableAmount);
        response.setPayableAmount(payableAmount);
        response.setOrderRequests(mapToOrderRequest(registerOrdersRequest.getOrdersList()));
        return response;
    }

    private List<OrderRequest> mapToOrderRequest(List<OrderRequest> orderRequests) {
        List<OrderRequest> responseOrderRequest = new ArrayList<>();
        orderRequests.forEach(request -> {
            OrderRequest orderRequest = new OrderRequest();
            orderRequest.setDrink(request.getDrink());
            orderRequest.setToppings(request.getToppings());
            responseOrderRequest.add(orderRequest);
        });
        return responseOrderRequest;
    }
}
