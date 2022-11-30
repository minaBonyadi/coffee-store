package com.bestseller.coffeestore.admin.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "ORDERS")
@AllArgsConstructor
@NoArgsConstructor
public class Orders {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "drink_id")
	Drink drink;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "topping_id")
	List<Topping> toppings;

	String trackingCode = getTrackingCode();

	private String setTrackingCode() {
		this.trackingCode = String.valueOf(UUID.randomUUID());
		return trackingCode;
	}

}
