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
		log.info("add a menu item successfully");
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping
	ResponseEntity<GeneralResponse> update(@RequestBody @Valid MenuItemRequest item) {
		adminMenuService.updateItem(item);
		log.info("update menu item successfully");
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping()
	ResponseEntity<GeneralResponse> delete(@RequestBody MenuItemRequest menuItemRequest) {
		adminMenuService.deleteItem(menuItemRequest);
		log.info("delete from menu item successfully");
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/order-report")
	ResponseEntity<OrderReportResponse> getOrderReports() {
		adminMenuService.orderReport();
		log.info("delete from menu item successfully");
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
