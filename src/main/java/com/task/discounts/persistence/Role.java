package com.task.discounts.persistence;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

/**
 * Role.
 *
 * @author AbdulMuhsin J. Al-Kandari
 */
@Document(collection = "roles")
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Role {
    @MongoId
    private String id;
    @Indexed(unique = true)
    private String name;
    private String description;
}
