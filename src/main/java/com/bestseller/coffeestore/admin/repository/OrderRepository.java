package com.bestseller.coffeestore.admin.repository;

import java.util.List;

import com.bestseller.coffeestore.admin.model.Order;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
	@Query(value = "select * from orders o group by o.drink_id", nativeQuery = true)
	List<Order> getOrdersReports();
}
