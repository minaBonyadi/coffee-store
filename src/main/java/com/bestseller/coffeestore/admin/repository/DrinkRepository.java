package com.bestseller.coffeestore.admin.repository;

import com.bestseller.coffeestore.admin.model.Drink;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrinkRepository extends CrudRepository<Drink, Long> {
}
