package com.craigwoodcock.fishingapp.repository;

import com.craigwoodcock.fishingapp.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByUsername(String username);

    Optional<User> getUserById(long id);

//    void delete(User user);
}