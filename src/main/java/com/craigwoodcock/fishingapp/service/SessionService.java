package com.craigwoodcock.fishingapp.service;

import com.craigwoodcock.fishingapp.exception.SessionNotFoundException;
import com.craigwoodcock.fishingapp.model.entity.*;
import com.craigwoodcock.fishingapp.model.id.AnglerSessionId;
import com.craigwoodcock.fishingapp.repository.AnglerRepository;
import com.craigwoodcock.fishingapp.repository.AnglerSessionRepository;
import com.craigwoodcock.fishingapp.repository.CatchRepository;
import com.craigwoodcock.fishingapp.repository.SessionRepository;
import com.craigwoodcock.fishingapp.utils.LbOzWeightConverter;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class SessionService {

    private static final Logger log = Logger.getLogger(SessionService.class.getName());

    private final SessionRepository sessionRepository;
    private final AnglerRepository anglerRepository;
    private final AnglerSessionRepository anglerSessionRepository;
    private final AnglerService anglerService;
    private final CatchRepository catchRepository;
    private final LbOzWeightConverter weightConverter;


    public SessionService(SessionRepository sessionRepository, AnglerRepository anglerRepository, AnglerSessionRepository anglerSessionRepository, AnglerService anglerService, CatchRepository catchRepository, LbOzWeightConverter weightConverter) {
        this.sessionRepository = sessionRepository;
        this.anglerRepository = anglerRepository;
        this.anglerSessionRepository = anglerSessionRepository;
        this.anglerService = anglerService;
        this.catchRepository = catchRepository;
        this.weightConverter = weightConverter;
    }


    @Transactional
    public Session createSession(User user, String venue, LocalDate startDate, int durationHours) {
        log.info("Creating new session");
        Session session = new Session();
        session.setUser(user);
        session.setVenue(venue);
        session.setStartDate(startDate);
        session.setDurationHours(durationHours);
        session = sessionRepository.save(session);

        Angler userAngler = anglerService.findOrCreateAngler(user.getName(), user.getEmail());
        createAnglerSession(session, userAngler);

        return session;
    }

    public long getSessionCount() {
        return sessionRepository.count();
    }

    @Transactional
    public void addAnglerToSession(Long sessionId, String name, String email) {
        Session session = getSessionById(sessionId);
        Angler angler = anglerService.findOrCreateAngler(name, email);
        createAnglerSession(session, angler);
    }


    private void createAnglerSession(Session session, Angler angler) {
        AnglerSessionId id = new AnglerSessionId(angler.getId(), session.getId());
        if (anglerSessionRepository.existsById(id)) {
            return; // already on this session, nothing to do
        }
        AnglerSession anglerSession = new AnglerSession();
        anglerSession.setId(id);
        anglerSession.setAngler(angler);
        anglerSession.setSession(session);
        anglerSession.setCreatedAt(Instant.now());
        anglerSessionRepository.save(anglerSession);
    }

    /**
     * Updates a session's venue, start date, and duration. Anglers are
     * managed separately via addAnglersToSession / removeAnglerFromSession,
     * so this only ever touches the session's own fields.
     *
     * @param sessionId     the id of the session being edited
     * @param venue         the updated venue
     * @param startDate     the updated start date
     * @param durationHours the updated duration in hours
     * @return the updated Session entity
     */
    @Transactional
    public Session updateSessionDetails(Long sessionId, String venue, LocalDate startDate, int durationHours) {
        Session session = getSessionById(sessionId);
        session.setVenue(venue);
        session.setStartDate(startDate);
        session.setDurationHours(durationHours);
        return sessionRepository.save(session);
    }


    public Session getSessionById(long id) throws SessionNotFoundException {

        return sessionRepository.findById(id).orElseThrow(() -> new SessionNotFoundException("That session no longer exists!"));

    }

    public List<Session> getAllSessions() {
        return sessionRepository.findAll();
    }

    public List<Session> getAllSessionsByUser(User user) {
        return sessionRepository.findByUserOrderByStartDateDesc(user);
    }

    public Session updateSession(Session session) {
        return sessionRepository.save(session);
    }

    @Transactional
    public void deleteSession(Long sessionId) {
        // delete associated anglers first.
        anglerSessionRepository.deleteBySessionId(sessionId);
        // then delete the session.
        sessionRepository.deleteById(sessionId);
    }

    @Transactional
    public void removeAnglerFromSession(Long sessionId, Long anglerId) {
        AnglerSessionId id = new AnglerSessionId(anglerId, sessionId);
        anglerSessionRepository.deleteById(id);

    }

    public List<Angler> getAnglersForSession(Long sessionId) {
        List<AnglerSession> anglerSessions = anglerSessionRepository.findBySessionId(sessionId);
        List<Angler> anglers = new ArrayList<>();
        for (AnglerSession anglerSession : anglerSessions) {
            anglers.add(anglerSession.getAngler());
        }
        return anglers;
    }

    /**
     * Calculates the total weight caught during a session, expressed in the
     * app's lb.oz notation.
     *
     * @param sessionId the id of the session
     * @return the total weight caught during the session, e.g. "11lb 0oz"
     */
    public String getTotalWeightForSession(Long sessionId) {
        List<Catch> catches = catchRepository.findBySessionId(sessionId);

        long totalOunces = 0;
        for (Catch c : catches) {
            totalOunces += weightConverter.toTotalOunces(c.getWeight());
        }

        return weightConverter.formatTotalOunces(totalOunces);
    }

    public boolean isOwner(Session session, User user) {
        return session.getUser().getId().equals(user.getId());
    }

    public boolean isUserAssociatedWithSession(Session session, User user) {
        if (isOwner(session, user)) {
            return true;
        }
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            return false;
        }
        return getAnglersForSession(session.getId()).stream()
                .anyMatch(angler -> user.getEmail().equalsIgnoreCase(angler.getEmail()));
    }

    public List<Session> getSessionsSharedWithUser(User user) {
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            return List.of();
        }
        List<AnglerSession> anglerSessions = anglerSessionRepository.findByAngler_EmailIgnoreCase(user.getEmail());
        List<Session> shared = new ArrayList<>();
        for (AnglerSession anglerSession : anglerSessions) {
            Session session = anglerSession.getSession();
            if (!isOwner(session, user)) {
                shared.add(session);
            }
        }
        return shared;
    }


}
