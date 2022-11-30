package com.bestseller.coffeestore.customer.discount;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {

    /**
     * 
     * @param totalAmount total amount
     * @return final amount after discount
     */
    @Override
    public Double promoteTwentyFivePercentDiscount(Double totalAmount) {
        return totalAmount - (totalAmount * 0.25);
    }

    /**
     * 
     * @param totalAmount total amount
     * @param amounts all order items amount
     * @return final amount after discount
     */
    @Override
    public Double getLowerAmountOrderItemDiscount(Double totalAmount, List<Double> amounts) {
        return totalAmount - amounts.stream().mapToDouble(Double::doubleValue).min().orElse(0.0);
    }

    /**
     * 
     * @param totalAmount total amount
     * @param amounts all order items amount
     * @return final amount after discount
     */
    @Override
    public Double getLowestDiscount(Double totalAmount, List<Double> amounts) {
        Double twentyFivePercentPromote = promoteTwentyFivePercentDiscount(totalAmount);
        Double LowerItemDiscountPromote = getLowerAmountOrderItemDiscount(totalAmount, amounts);
        return twentyFivePercentPromote > LowerItemDiscountPromote ? twentyFivePercentPromote : LowerItemDiscountPromote;
    }
}
