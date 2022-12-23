package com.bestseller.coffeestore.customer;

import com.bestseller.coffeestore.customer.dto.request.RegisterOrdersRequest;
import com.bestseller.coffeestore.customer.dto.response.OrderReceiptResponse;
import com.bestseller.coffeestore.customer.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/orders")
@RestController
@RequiredArgsConstructor
public class OrderResource {

	private final OrderService orderService;

	@PostMapping("/register")
	ResponseEntity<OrderReceiptResponse> registerOrder(@RequestBody @Valid RegisterOrdersRequest registerOrdersRequest) {
		log.info("gonna register order request");
		return new ResponseEntity<>(orderService.registerOrder(registerOrdersRequest), HttpStatus.OK);
	}
}
