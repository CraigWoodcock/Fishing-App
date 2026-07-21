package com.craigwoodcock.fishingapp.repository;

import com.craigwoodcock.fishingapp.model.entity.Angler;
import com.craigwoodcock.fishingapp.model.entity.Catch;
import com.craigwoodcock.fishingapp.model.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatchRepository extends JpaRepository<Catch, Long> {
    List<Catch> findByAngler(Angler angler);


    List<Catch> findBySession(Session session);

    /**
     * Finds all catches recorded against a given session, so the session
     * view page can list every fish caught during that trip.
     *
     * @param sessionId the id of the session
     * @return the list of catches belonging to that session
     */
    List<Catch> findBySessionId(Long sessionId);

    /**
     * Finds all catches recorded by a specific angler during a specific
     * session, so a per-angler weight total can be shown alongside the
     * angler's name on the session view page.
     *
     * @param sessionId the id of the session
     * @param anglerId  the id of the angler
     * @return the list of catches that angler made during that session
     */
    List<Catch> findBySessionIdAndAnglerId(Long sessionId, Long anglerId);
}