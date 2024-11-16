package com.craigwoodcock.fishingapp.repository;

import com.craigwoodcock.fishingapp.model.entity.JwtToken;
import com.craigwoodcock.fishingapp.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JwtTokenRepository extends JpaRepository<JwtToken, Long> {
    List<JwtToken> findByUserAndRevokedFalse(User user);

    JwtToken findByToken(String token);
}