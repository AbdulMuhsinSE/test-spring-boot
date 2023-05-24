package com.task.discounts.discount.service;

import static org.junit.jupiter.api.Assertions.*;

import com.task.discounts.discount.payload.BillRequest;
import com.task.discounts.discount.payload.Item;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.SpyBean;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

class DiscountServiceTest {
    DiscountService srv = Mockito.spy(DiscountService.class);
    static BillRequest billRequest = null;
    static LocalDate createdDate = null;

    @BeforeAll
    public static void setData() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("groceries", "milk", new BigDecimal(35)));
        items.add(new Item("electronics", "tv", new BigDecimal(100)));
        items.add(new Item("electronics", "pc", new BigDecimal(1000)));

        billRequest = new BillRequest("1", items);
        createdDate = LocalDate.of(2020, 3, 12);
    }


    @Test
    void calculateNetPayable_Employee() {
        Set<String> roles = Set.of("employee", "user");
        assertEquals(new BigDecimal(765).setScale(3, RoundingMode.HALF_UP), srv.calculateNetPayable(billRequest, roles, createdDate));
    }

    @Test
    void calculateNetPayable_Affiliate() {
        Set<String> roles = Set.of("affiliate", "user");
        assertEquals(new BigDecimal(975).setScale(3, RoundingMode.HALF_UP), srv.calculateNetPayable(billRequest, roles, createdDate));
    }

    @Test
    void calculateNetPayable_User() {
        Set<String> roles = Set.of("user");
        assertEquals(new BigDecimal(1030).setScale(3, RoundingMode.HALF_UP), srv.calculateNetPayable(billRequest, roles, createdDate));
    }

    @Test
    void calculateNetPayable_User_New() {
        Set<String> roles = Set.of("user");
        assertEquals(new BigDecimal(1080).setScale(3, RoundingMode.HALF_UP), srv.calculateNetPayable(billRequest, roles, LocalDate.now()));
    }
}