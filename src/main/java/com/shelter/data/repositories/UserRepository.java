package com.shelter.data.repositories;

import com.shelter.data.entities.Role;
import com.shelter.data.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findUsersByRole(Role role);
}
