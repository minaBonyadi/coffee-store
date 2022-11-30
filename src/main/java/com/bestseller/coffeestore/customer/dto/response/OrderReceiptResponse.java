package com.bestseller.coffeestore.customer.dto.response;

import com.bestseller.coffeestore.customer.dto.request.OrderRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class OrderReceiptResponse {

    Double totalAmount;

    Double amountAfterDiscount;

    Double payableAmount;

    List<OrderRequest> orderRequests;

    String description;
}
