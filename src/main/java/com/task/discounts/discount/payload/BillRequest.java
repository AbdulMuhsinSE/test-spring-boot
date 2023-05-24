package com.task.discounts.discount.payload;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

/**
 * BillRequest.
 *
 * @author AbdulMuhsin J. Al-Kandari
 */
@Data
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BillRequest {
    private String id;
    private List<Item> items;
}
