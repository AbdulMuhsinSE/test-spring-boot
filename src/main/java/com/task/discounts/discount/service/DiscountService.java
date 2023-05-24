package com.task.discounts.discount.service;

import com.task.discounts.discount.payload.BillRequest;
import com.task.discounts.discount.payload.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Set;

/**
 * DiscountService.
 *
 * @author AbdulMuhsin J. Al-Kandari
 */
@Service
@Slf4j
public class DiscountService {
    public BigDecimal calculateNetPayable(BillRequest bill, Set<String> roles, LocalDate createdDate) {
        List<Item> itemsNotDiscountable = bill.getItems().stream()
                .filter(item -> item.getCategory().equals("groceries")).toList();
        List<Item> itemsDiscountable = bill.getItems()
                .stream().filter(item -> !item.getCategory().equals("groceries")).toList();

        BigDecimal itemsNotDiscountablePrice = itemsNotDiscountable.stream()
                .map(Item::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal itemsDiscountablePrice = itemsDiscountable.stream()
                .map(Item::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal rateAfterDiscount = BigDecimal.ONE;
        if(roles.contains("employee")) {
            rateAfterDiscount = new BigDecimal("0.7");
        } else if (roles.contains("affiliate")) {
            rateAfterDiscount = new BigDecimal("0.9");
        } else if (Period.between(createdDate, LocalDate.now()).getYears() >= 2) {
            rateAfterDiscount = new BigDecimal("0.95");
        }

        itemsDiscountablePrice = itemsDiscountablePrice.multiply(rateAfterDiscount);
        BigDecimal postPercentDiscountTotal = itemsDiscountablePrice.add(itemsNotDiscountablePrice);
        log.debug("Post Percent Discount: " + postPercentDiscountTotal.toPlainString());
        int numberOfHundreds = (postPercentDiscountTotal.intValue()/100);
        log.debug("Number of Hundreds: " + numberOfHundreds);

        return postPercentDiscountTotal.subtract(BigDecimal.valueOf(numberOfHundreds*5))
                .setScale(3, RoundingMode.HALF_UP);
    }
}
