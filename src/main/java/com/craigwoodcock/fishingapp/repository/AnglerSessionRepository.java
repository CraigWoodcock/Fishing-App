package com.craigwoodcock.fishingapp.repository;

import com.craigwoodcock.fishingapp.model.entity.AnglerSession;
import com.craigwoodcock.fishingapp.model.id.AnglerSessionId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnglerSessionRepository extends JpaRepository<AnglerSession, AnglerSessionId> {
    List<AnglerSession> findBySessionId(Long sessionId);

    List<AnglerSession> findBySessionUserId(Long id);

    void deleteBySessionId(Long sessionId);
}