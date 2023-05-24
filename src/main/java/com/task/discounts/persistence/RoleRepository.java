package com.task.discounts.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

/**
 * RoleRepository.
 *
 * @author AbdulMuhsin J. Al-Kandari
 */
public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(String name);
}
