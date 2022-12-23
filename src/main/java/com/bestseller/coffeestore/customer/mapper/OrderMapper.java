package com.bestseller.coffeestore.customer.mapper;

import com.bestseller.coffeestore.admin.model.Drink;
import com.bestseller.coffeestore.admin.model.Orders;
import com.bestseller.coffeestore.admin.model.Topping;
import com.bestseller.coffeestore.customer.dto.request.OrderRequest;
import com.bestseller.coffeestore.customer.dto.request.RegisterOrdersRequest;
import com.bestseller.coffeestore.customer.dto.response.OrderReceiptResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface OrderMapper {

    /**
     *
     * @param drink drink
     * @param toppings list of toppings
     * @return orders
     */
    @Mapping(target = "toppings", source = "toppings")
    @Mapping(target = "drink", source = "drink")
    Orders mapToOrder(Drink drink, List<Topping> toppings);

    /**
     *
     * @param request register orders request
     * @param totalAmount total amount
     * @param payableAmount payable amount
     * @return order receipt response
     */
    @Mapping(target = "orderRequests", source = "request", qualifiedByName = "mapToOrderRequest")
    @Mapping(target = "description", source = "totalAmount, payableAmount",
            qualifiedByName = "getDescription")
    @Mapping(target = "totalAmount", source = "totalAmount")
    @Mapping(target = "payableAmount", source = "payableAmount")
    @Mapping(target = "amountAfterDiscount", source = "payableAmount")
    OrderReceiptResponse createReceiptResponse(RegisterOrdersRequest request,
                                                       double totalAmount, double payableAmount);

    @Named("getDescription")
    default String getDescription(double totalAmount, double payableAmount) {
        if (totalAmount == payableAmount) {
            return "discount not included";
        } else {
            return "discount included";
        }
    }

    @Named("mapToOrderRequest")
    default List<OrderRequest> mapToOrderRequest(List<OrderRequest> orderRequests) {
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
