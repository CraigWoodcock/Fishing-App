package com.craigwoodcock.fishingapp.service;

import com.craigwoodcock.fishingapp.model.Angler;
import com.craigwoodcock.fishingapp.model.Catch;
import com.craigwoodcock.fishingapp.model.Session;
import com.craigwoodcock.fishingapp.model.User;
import com.craigwoodcock.fishingapp.repository.AnglerRepository;
import com.craigwoodcock.fishingapp.repository.CatchRepository;
import com.craigwoodcock.fishingapp.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

public Session createSession(Session session) {
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
