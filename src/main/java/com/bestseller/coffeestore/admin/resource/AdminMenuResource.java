package com.bestseller.coffeestore.admin.resource;

import javax.validation.Valid;

import com.bestseller.coffeestore.admin.AdminMenuService;
import com.bestseller.coffeestore.admin.dto.GeneralResponse;
import com.bestseller.coffeestore.admin.dto.MenuItemRequest;
import com.bestseller.coffeestore.admin.dto.OrderReportResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/admin/menu")
@RequiredArgsConstructor
public class AdminMenuResource {

	private final AdminMenuService adminMenuService;

	@PostMapping
	ResponseEntity<GeneralResponse> create(@RequestBody @Valid MenuItemRequest item) {
		adminMenuService.addItem(item);
		log.info("gonna add a menu item {}", item.getId());
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping
	ResponseEntity<GeneralResponse> update(@RequestBody @Valid MenuItemRequest item) {
		adminMenuService.updateItem(item);
		log.info("gonna update menu item {}", item.getId());
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping()
	ResponseEntity<GeneralResponse> delete(@RequestBody MenuItemRequest item) {
		adminMenuService.deleteItem(item);
		log.info("gonna delete from menu item {}", item.getId());
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/order-report")
	ResponseEntity<OrderReportResponse> getOrderReports() {
		adminMenuService.orderReport();
		log.info("gonna get order report");
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
