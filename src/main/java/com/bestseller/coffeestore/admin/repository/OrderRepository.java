package com.bestseller.coffeestore.admin.repository;

import com.bestseller.coffeestore.admin.model.Orders;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Orders, Long> {
	@Query(value = "select o from Orders o group by o.drink.id")
	List<Orders> getOrdersReports();
}
