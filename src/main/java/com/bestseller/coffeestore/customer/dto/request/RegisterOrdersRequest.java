package com.bestseller.coffeestore.customer.dto.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterOrdersRequest {

    String trackingCode;

    List<OrderRequest> ordersList;

}
