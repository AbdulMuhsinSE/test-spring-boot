package com.task.discounts.discount.controller;

import com.task.discounts.authentication.model.UserDetailsImpl;
import com.task.discounts.discount.payload.BillRequest;
import com.task.discounts.discount.service.DiscountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * DiscountController.
 *
 * @author AbdulMuhsin J. Al-Kandari
 */
@RestController
@RequestMapping("/discount")
@RequiredArgsConstructor
public class DiscountController {
    private final DiscountService discountService;

    @PostMapping("/calculate")
    public ResponseEntity<String> calculateNetPayable(@RequestBody @Valid BillRequest bill) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Set<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
        return ResponseEntity.ok(discountService.calculateNetPayable(bill, roles, userDetails.getCreatedDate()).toPlainString());
    }
}
