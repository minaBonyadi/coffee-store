package com.bestseller.coffeestore.customer.discount;

import java.util.List;

public interface DiscountService {

    Double promoteTwentyFivePercentDiscount(Double totalAmount);

    Double promoteLowerAmountOrderItemDiscount(Double totalAmount, List<Double> amounts);

    Double promoteLowestDiscount(Double totalAmount, List<Double> amounts);

}
