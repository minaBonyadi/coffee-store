package com.bestseller.coffeestore.customer.discount;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {


    @Override
    public Double promoteTwentyFivePercentDiscount(Double totalAmount) {

        return totalAmount - (totalAmount * 0.25);
    }

    @Override
    public Double promoteLowerAmountOrderItemDiscount(Double totalAmount, List<Double> amounts) {
        return totalAmount - amounts.stream().mapToDouble(Double::doubleValue).min().getAsDouble();
    }

    @Override
    public Double promoteLowestDiscount(Double totalAmount, List<Double> amounts) {
        Double twentyFivePercentPromote = promoteTwentyFivePercentDiscount(totalAmount);
        Double LowerItemDiscountPromote = promoteLowerAmountOrderItemDiscount(totalAmount, amounts);
        return twentyFivePercentPromote > LowerItemDiscountPromote ? twentyFivePercentPromote : LowerItemDiscountPromote;
    }
}
