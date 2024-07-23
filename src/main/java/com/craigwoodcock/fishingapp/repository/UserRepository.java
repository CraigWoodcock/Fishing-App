package com.craigwoodcock.fishingapp.repository;

import com.craigwoodcock.fishingapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}