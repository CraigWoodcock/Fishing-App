package com.craigwoodcock.fishingapp.repository;

import com.craigwoodcock.fishingapp.model.Angler;
import com.craigwoodcock.fishingapp.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnglerRepository extends JpaRepository<Angler, Long> {
  List<Angler> findBySession(Session session);

    List<Angler> findBySessionId(Long sessionId);
}