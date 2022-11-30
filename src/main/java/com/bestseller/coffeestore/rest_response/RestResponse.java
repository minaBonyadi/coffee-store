package com.bestseller.coffeestore.rest_response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RestResponse {
    private RestResponseType code;
    private String message;
}
