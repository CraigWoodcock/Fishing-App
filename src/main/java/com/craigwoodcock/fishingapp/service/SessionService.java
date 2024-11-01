package com.craigwoodcock.fishingapp.service;

import com.craigwoodcock.fishingapp.model.*;
import com.craigwoodcock.fishingapp.repository.AnglerRepository;
import com.craigwoodcock.fishingapp.repository.AnglerSessionRepository;
import com.craigwoodcock.fishingapp.repository.SessionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class SessionService {

    private static final Logger log = Logger.getLogger(SessionService.class.getName());

    private final SessionRepository sessionRepository;
    private final AnglerRepository anglerRepository;
    private final AnglerSessionRepository anglerSessionRepository;
    private final AnglerService anglerService;


    public SessionService(SessionRepository sessionRepository, AnglerRepository anglerRepository, AnglerSessionRepository anglerSessionRepository, AnglerService anglerService) {
        this.sessionRepository = sessionRepository;
        this.anglerRepository = anglerRepository;
        this.anglerSessionRepository = anglerSessionRepository;
        this.anglerService = anglerService;
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


// Create a String Array of anglers names (seperated by a comma-seperated list)
// if the anglersList is not empty.
        if (anglersList != null && !anglersList.trim().isEmpty()) {
            String[] anglerNames = anglersList.split(",");

// Find or create each angler in the list
            for (String anglerName : anglerNames) {
                if (!anglerName.trim().equals(user.getName())) {
                    Angler angler = anglerService.findOrCreateAngler(anglerName.trim());
                    createAnglerSession(session, angler);
                }
            }
        }
        return session;
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

    public Optional<Session> getSessionById(long id) {
        return sessionRepository.findById(id);
    }

    public List<Session> getAllSessionsByUser(User user) {
        return sessionRepository.findByUser(user);
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

//    public Catch addCatchToSession(Long sessionId, Long anglerId, Catch capture) {
//        Session session = sessionRepository.findById(sessionId).orElseThrow(()-> new RuntimeException("Session not found"));
//        Angler angler = anglerRepository.findById(anglerId).orElseThrow(()-> new RuntimeException("Angler not found"));
//        capture.setSession(session);
//        capture.setAngler(angler);
//        return catchRepository.save(capture);
//    }
//
//    public List<Catch> getCatchesBySession(Long sessionId) {
//        return catchRepository.findBySessionId(sessionId);
//    }
//
//    public void removeCatchFromSession(Long sessionId, Long catchId) {
//        catchRepository.deleteById(catchId);
//    }
}
