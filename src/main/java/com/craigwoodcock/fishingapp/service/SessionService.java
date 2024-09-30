package com.craigwoodcock.fishingapp.service;

import com.craigwoodcock.fishingapp.model.Angler;
import com.craigwoodcock.fishingapp.model.Catch;
import com.craigwoodcock.fishingapp.model.Session;
import com.craigwoodcock.fishingapp.model.User;
import com.craigwoodcock.fishingapp.repository.AnglerRepository;
import com.craigwoodcock.fishingapp.repository.CatchRepository;
import com.craigwoodcock.fishingapp.repository.SessionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class SessionService {
    private final SessionRepository sessionRepository;
    private final AnglerRepository anglerRepository;
    private final CatchRepository catchRepository;
    private final AnglerService anglerService;

    @Autowired
    public SessionService(SessionRepository sessionRepository, AnglerRepository anglerRepository, CatchRepository catchRepository, AnglerService anglerService) {
        this.sessionRepository = sessionRepository;
        this.anglerRepository = anglerRepository;
        this.catchRepository = catchRepository;
        this.anglerService = anglerService;
    }
@Transactional
public Session createSession(User user, String venue, LocalDate startDate,
                             int durationHours, String anglersList) {

        Session session = new Session();
        session.setUser(user);
        session.setVenue(venue);
        session.setStartDate(startDate);
        session.setDurationHours(durationHours);
        session = sessionRepository.save(session);


// create a new list of Type Angler to store this sessions anglers
        Set<Angler> sessionAnglers = new HashSet<>();
// Find or create the userAngler, this will be the logged in user.
        Angler userAngler = anglerService.findOrCreateAngler(user.getName());

        sessionAnglers.add(userAngler);



// Create a String Array of anglers names (seperated by a comma-seperated list)
// if the anglersList is not empty.
    if (anglersList != null && !anglersList.trim().isEmpty()){
        String[] anglerNames = anglersList.split(",");

// Find or create each angler in the list
        for (String anglerName : anglerNames) {
           Angler angler = anglerService.findOrCreateAngler(anglerName.trim());
                sessionAnglers.add(angler);

        }
    }

// add this session to all anglers.
    for (Angler angler : sessionAnglers) {
        if (angler.getSessions() == null || angler.getSessions().isEmpty()) {
            angler.setSessions(new ArrayList<>());
        }
        angler.getSessions().add(session);
        anglerRepository.save(angler);
    }
// Set the Anglers for this Session to the sessionAnglers
        session.setAnglers(sessionAnglers);
// Save the Session
        return sessionRepository.save(session);
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

public void deleteSession(Long id) {
        sessionRepository.deleteById(id);
}

    public void removeAnglerFromSession(Long sessionId, Long anglerId) {
        anglerRepository.deleteById(anglerId);
    }

    public Catch addCatchToSession(Long sessionId, Long anglerId, Catch capture) {
        Session session = sessionRepository.findById(sessionId).orElseThrow(()-> new RuntimeException("Session not found"));
        Angler angler = anglerRepository.findById(anglerId).orElseThrow(()-> new RuntimeException("Angler not found"));
        capture.setSession(session);
        capture.setAngler(angler);
        return catchRepository.save(capture);
    }

    public List<Catch> getCatchesBySession(Long sessionId) {
        return catchRepository.findBySessionId(sessionId);
    }

    public void removeCatchFromSession(Long sessionId, Long catchId) {
        catchRepository.deleteById(catchId);
    }
}
