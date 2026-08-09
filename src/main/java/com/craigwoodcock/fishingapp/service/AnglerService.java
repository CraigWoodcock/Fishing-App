package com.craigwoodcock.fishingapp.service;

import com.craigwoodcock.fishingapp.exception.MissingAnglerEmailException;
import com.craigwoodcock.fishingapp.model.entity.Angler;
import com.craigwoodcock.fishingapp.model.entity.AnglerSession;
import com.craigwoodcock.fishingapp.model.entity.User;
import com.craigwoodcock.fishingapp.repository.AnglerRepository;
import com.craigwoodcock.fishingapp.repository.AnglerSessionRepository;
import com.craigwoodcock.fishingapp.repository.SessionRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Logger;

@Service
public class AnglerService {

    private static final Logger log = Logger.getLogger(AnglerService.class.getName());

    private final AnglerRepository anglerRepository;
    private final AnglerSessionRepository anglerSessionRepository;
    private final SessionRepository sessionRepository;

    public AnglerService(AnglerRepository anglerRepository, AnglerSessionRepository anglerSessionRepository, SessionRepository sessionRepository) {
        this.anglerRepository = anglerRepository;
        this.anglerSessionRepository = anglerSessionRepository;
        this.sessionRepository = sessionRepository;
    }

    /**
     * Finds an existing angler by email, or creates a new one. Email is
     * required and unique, so it's the sole basis for matching — two
     * anglers can share a name, but never an email. This means the same
     * real person is always resolved to the same Angler row, however many
     * different sessions they're added to.
     *
     * @param name  the angler's display name
     * @param email the angler's email; required
     * @return the existing or newly created Angler
     * @throws MissingAnglerEmailException if email is null or blank
     */
    public Angler findOrCreateAngler(String name, String email) {
        if (email == null || email.isBlank()) {
            throw new MissingAnglerEmailException("An angler's email is required.");
        }

        Optional<Angler> existing = anglerRepository.findByEmailIgnoreCase(email);
        if (existing.isPresent()) {
            return existing.get();
        }

        Angler angler = new Angler();
        angler.setName(name);
        angler.setEmail(email);
        return anglerRepository.save(angler);
    }

    public List<Angler> findAllAnglersByUser(User user) {
        log.info("Finding all Anglers by user: " + user);
        List<AnglerSession> anglerSessions = anglerSessionRepository.findBySessionUserId(user.getId());
        Set<Angler> uniqueAnglers = new HashSet<>();
        for (AnglerSession anglerSession : anglerSessions) {
            uniqueAnglers.add(anglerSession.getAngler());
        }
        log.info("Found " + uniqueAnglers.size() + " Anglers");
        return new ArrayList<>(uniqueAnglers);
    }
}