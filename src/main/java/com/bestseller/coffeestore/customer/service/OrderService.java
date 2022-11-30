package com.bestseller.coffeestore.customer.service;

import com.bestseller.coffeestore.customer.dto.response.OrderReceiptResponse;
import com.bestseller.coffeestore.customer.dto.request.RegisterOrdersRequest;

public interface OrderService {
    OrderReceiptResponse registerOrder(RegisterOrdersRequest registerOrdersRequest);
}
