package com.bestseller.coffeestore.customer;

import javax.validation.Valid;

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
public class OrderResource {

	@PostMapping
	ResponseEntity<OrderReceiptResponse> create(@RequestBody @Valid OrderRequest orderRequest) {
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
