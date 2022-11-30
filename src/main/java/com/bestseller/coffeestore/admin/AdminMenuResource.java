package com.bestseller.coffeestore.admin;

import com.bestseller.coffeestore.admin.service.AdminMenuService;
import com.bestseller.coffeestore.admin.dto.GeneralResponse;
import com.bestseller.coffeestore.admin.dto.MenuItemRequest;
import com.bestseller.coffeestore.admin.dto.OrderReportResponse;
import com.bestseller.coffeestore.exception.DrinkNotFoundException;
import com.bestseller.coffeestore.exception.ToppingNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
	ResponseEntity<GeneralResponse> update(@RequestBody @Valid MenuItemRequest item)
			throws DrinkNotFoundException, ToppingNotFoundException {
		adminMenuService.updateItem(item);
		log.info("gonna update menu item {}", item.getId());
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping()
	ResponseEntity<GeneralResponse> delete(@RequestBody MenuItemRequest item)
			throws DrinkNotFoundException, ToppingNotFoundException {
		adminMenuService.deleteItem(item);
		log.info("gonna delete from menu item {}", item.getId());
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/order-report")
	ResponseEntity<OrderReportResponse> getOrderReports() {
		log.info("gonna get order report");
		return new ResponseEntity<>(adminMenuService.orderReport(), HttpStatus.OK);
	}
}
