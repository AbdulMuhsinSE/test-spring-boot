package com.task.discounts.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * RoleEnum.
 *
 * @author AbdulMuhsin J. Al-Kandari
 */
@AllArgsConstructor
@Getter
public enum RoleEnum {
    USER_ROLE("user"),
    EMPLOYEE_ROLE("employee"),
    AFFILIATE_ROLE("affiliate");

    private final String role;
}
