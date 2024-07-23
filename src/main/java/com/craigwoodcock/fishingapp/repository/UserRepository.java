package com.craigwoodcock.fishingapp.repository;

import com.craigwoodcock.fishingapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}