package com.bestseller.coffeestore.admin.dto;

import java.util.Map;

import com.bestseller.coffeestore.admin.model.Drink;
import com.bestseller.coffeestore.admin.model.Topping;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderReportResponse {

	Map<Drink, Topping> orderReports;

}
