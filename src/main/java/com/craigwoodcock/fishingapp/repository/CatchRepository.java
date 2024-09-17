package com.craigwoodcock.fishingapp.repository;

import com.craigwoodcock.fishingapp.model.Catch;
import com.craigwoodcock.fishingapp.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatchRepository extends JpaRepository<Catch, Long> {
    List<Catch> findByAngler(Angler angler);

    List<Catch> findBySession(Session session);

    List<Catch> findBySessionId(Long sessionId);
}