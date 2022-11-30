package com.bestseller.coffeestore.customer.dto.response;

import com.bestseller.coffeestore.customer.dto.request.OrderRequest;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@RequiredArgsConstructor
public class OrderReceiptResponse {

    Double totalAmount;

    Double amountAfterDiscount;

    Double payableAmount;

    List<OrderRequest> orderRequests;

    String description;
}
