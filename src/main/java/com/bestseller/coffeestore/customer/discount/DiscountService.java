package com.bestseller.coffeestore.customer.discount;

import java.util.List;

public interface DiscountService {

    Double promoteTwentyFivePercentDiscount(Double totalAmount);

    Double getLowerAmountOrderItemDiscount(Double totalAmount, List<Double> amounts);

    Double getLowestDiscount(Double totalAmount, List<Double> amounts);

}
