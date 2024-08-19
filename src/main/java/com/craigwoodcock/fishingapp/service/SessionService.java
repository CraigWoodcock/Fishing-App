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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SessionService {
    private final SessionRepository sessionRepository;
    private final AnglerRepository anglerRepository;
    private final CatchRepository catchRepository;

    @Autowired
    public SessionService(SessionRepository sessionRepository, AnglerRepository anglerRepository, CatchRepository catchRepository) {
        this.sessionRepository = sessionRepository;
        this.anglerRepository = anglerRepository;
        this.catchRepository = catchRepository;
    }
@Transactional
public Session createSession(User user, String venue, LocalDate startdate,
                             int durationHours, String anglersList) {

        Session session = new Session();
        session.setUser(user);
        session.setVenue(venue);
        session.setStartDate(startdate);
        session.setDurationHours(durationHours);
        session = sessionRepository.save(session);

// Create a new List of type Angler
        List<Angler> anglers = new ArrayList<>();
// Create a new Angler for the user, this will be the logged-in User.
        Angler userAngler = new Angler();
// Set the name of the new Angler to the logged-in User
        userAngler.setName(user.getName());
// Set the Session to the passed in session
        userAngler.setSession(session);
// Add the logged-in User to the Anglers List
        anglers.add(userAngler);
// Create a String Array of anglers names (seperated by a comma-seperated list)
    if (anglersList.matches(".*[a-zA-Z].*")){
        String[] anglerNames = anglersList.split(",");
        for (String anglerName : anglerNames) {
            Angler angler = new Angler();
            angler.setName(anglerName.trim());
            angler.setSession(session);
            anglers.add(angler);
        }
    }
// Save the Anglers List
    anglerRepository.saveAll(anglers);
// Set the Anglers for this Session to the Anglers List
        session.setAnglers(anglers);
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

public Angler addAnglerToSession(Long sessionId, Angler angler) {
        Session session = sessionRepository.findById(sessionId).orElseThrow(()-> new RuntimeException("Session not found"));
        angler.setSession(session);
    return anglerRepository.save(angler);
    }

    public List<Angler> getanglersBySession(Long sessionId) {
        return anglerRepository.findBySessionId(sessionId);
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
