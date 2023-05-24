package com.task.discounts.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

/**
 * UserRepository.
 *
 * @author AbdulMuhsin J. Al-Kandari
 */
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
}
