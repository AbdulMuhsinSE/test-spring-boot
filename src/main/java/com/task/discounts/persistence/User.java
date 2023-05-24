package com.task.discounts.persistence;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * User.
 *
 * @author AbdulMuhsin J. Al-Kandari
 */
@Document(collection = "users")
@Data
public class User {
    @MongoId
    private String id;

    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Size(min = 8)
    private String password;

    private Set<Role> roles = new HashSet<>();

    @CreatedDate
    private LocalDate createdDate;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
