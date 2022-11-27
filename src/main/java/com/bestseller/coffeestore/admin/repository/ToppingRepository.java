package com.bestseller.coffeestore.admin.repository;

import com.bestseller.coffeestore.admin.model.Topping;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToppingRepository extends CrudRepository<Topping, Long> {
}
