package com.bestseller.coffeestore.customer.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class RegisterOrdersRequest {
    List<OrderRequest> ordersList;
}
