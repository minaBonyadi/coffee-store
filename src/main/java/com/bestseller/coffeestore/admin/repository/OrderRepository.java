package com.bestseller.coffeestore.admin.repository;

import java.util.List;

import com.bestseller.coffeestore.admin.model.Order;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
	List<Order> findAll();
}
