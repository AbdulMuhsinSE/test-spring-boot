package com.task.discounts.authentication.payload;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

/**
 * JwtResponse.
 *
 * @author AbdulMuhsin J. Al-Kandari
 */
@Data
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class JwtResponse {
    private String token;
    final private String type = "Bearer";
    private String id;
    private String username;
    private List<String> roles;
}
