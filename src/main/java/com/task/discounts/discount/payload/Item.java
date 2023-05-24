package com.task.discounts.discount.payload;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;

/**
 * Item.
 *
 * @author AbdulMuhsin J. Al-Kandari
 */
@Data
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Item {
    private String category;
    private String name;
    private BigDecimal price;
}
