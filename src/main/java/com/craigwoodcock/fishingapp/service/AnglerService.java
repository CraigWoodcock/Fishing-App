package com.craigwoodcock.fishingapp.service;

import com.craigwoodcock.fishingapp.model.Angler;
import com.craigwoodcock.fishingapp.model.AnglerSession;
import com.craigwoodcock.fishingapp.model.Session;
import com.craigwoodcock.fishingapp.model.User;
import com.craigwoodcock.fishingapp.repository.AnglerRepository;
import com.craigwoodcock.fishingapp.repository.AnglerSessionRepository;
import com.craigwoodcock.fishingapp.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Angler findOrCreateAngler(String name) {
        Optional<Angler> existingAngler = anglerRepository.findByName(name);
        if (existingAngler.isPresent()) {
            log.info("Angler: " + existingAngler.get().getName() + " already exists");
            return existingAngler.get();
        } else {
            log.info("Creating new Angler.... " );
            Angler newAngler = new Angler();
            newAngler.setName(name);
            log.info("Angler: " + newAngler.getName() + " created");
            return anglerRepository.save(newAngler);
        }
    }

    public List<Angler> findAllAnglersByUser(User user) {
        log.info("Finding all Anglers by user: "+ user);
        List<AnglerSession> anglerSessions = anglerSessionRepository.findBySessionUserId(user.getId());
        Set<Angler> uniqueAnglers = new HashSet<>();
        for (AnglerSession anglerSession : anglerSessions) {
            uniqueAnglers.add(anglerSession.getAngler());
        }
        log.info("Found " + uniqueAnglers.size() + " Anglers");
        return new ArrayList<>(uniqueAnglers);
    }
}
