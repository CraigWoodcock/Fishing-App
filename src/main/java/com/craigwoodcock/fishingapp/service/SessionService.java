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
    public Session createSession(User user, String venue, LocalDate startDate,
                                 int durationHours, String anglersList) {
        log.info("Creating new session");
        Session session = new Session();
        session.setUser(user);
        session.setVenue(venue);
        session.setStartDate(startDate);
        session.setDurationHours(durationHours);
        session = sessionRepository.save(session);
        log.info("Session created with id " + session.getId());

        // Find or create the userAngler, this will be the logged in user.
        Angler userAngler = anglerService.findOrCreateAngler(user.getName());
        createAnglerSession(session, userAngler);

        addAnglersToSession(session.getId(), anglersList);
        return session;
    }

    /**
     * Adds each named angler in a comma-separated list to a session,
     * creating any angler that doesn't already exist and skipping anyone
     * already attached to the session. Shared by session creation and
     * session editing so the parsing/dedup rule lives in one place.
     *
     * @param sessionId   the session to add anglers to
     * @param anglersList a comma-separated list of angler names, may be null or blank
     */
    @Transactional
    public void addAnglersToSession(Long sessionId, String anglersList) {
        if (anglersList == null || anglersList.trim().isEmpty()) {
            return;
        }
        Session session = getSessionById(sessionId);
        List<Angler> existingAnglers = getAnglersForSession(sessionId);

        for (String anglerName : anglersList.split(",")) {
            String trimmedName = anglerName.trim();
            if (trimmedName.isEmpty()) {
                continue;
            }
            boolean alreadyOnSession = existingAnglers.stream()
                    .anyMatch(a -> a.getName().equalsIgnoreCase(trimmedName));
            if (!alreadyOnSession) {
                Angler angler = anglerService.findOrCreateAngler(trimmedName);
                createAnglerSession(session, angler);
            }
        }
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

    private void createAnglerSession(Session session, Angler angler) {
        AnglerSession anglerSession = new AnglerSession();
        AnglerSessionId id = new AnglerSessionId(angler.getId(), session.getId());
        anglerSession.setId(id);
        anglerSession.setAngler(angler);
        anglerSession.setSession(session);
        anglerSession.setCreatedAt(Instant.now());
        anglerSessionRepository.save(anglerSession);
    }

    public Session getSessionById(long id) throws SessionNotFoundException {

        return sessionRepository.findById(id).orElseThrow(() -> new SessionNotFoundException("That session no longer exists!"));

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


}
