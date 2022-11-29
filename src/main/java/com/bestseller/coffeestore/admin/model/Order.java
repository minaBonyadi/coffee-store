package com.bestseller.coffeestore.admin.model;

import java.math.BigInteger;
import java.util.Set;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ORDER")
@AllArgsConstructor
@NoArgsConstructor
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@ManyToOne
	@JoinColumn(name = "drink_id")
	Drink drink;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	Set<Topping> toppings;

	BigInteger amount;

	BigInteger discount;
}
