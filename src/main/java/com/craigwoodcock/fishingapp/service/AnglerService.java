package com.craigwoodcock.fishingapp.service;

import com.craigwoodcock.fishingapp.exception.AnglerAlreadyExistsException;
import com.craigwoodcock.fishingapp.exception.AnglerHasRecordsException;
import com.craigwoodcock.fishingapp.exception.AnglerNotFoundException;
import com.craigwoodcock.fishingapp.exception.MissingAnglerEmailException;
import com.craigwoodcock.fishingapp.model.entity.Angler;
import com.craigwoodcock.fishingapp.model.entity.AnglerSession;
import com.craigwoodcock.fishingapp.model.entity.User;
import com.craigwoodcock.fishingapp.repository.AnglerRepository;
import com.craigwoodcock.fishingapp.repository.AnglerSessionRepository;
import com.craigwoodcock.fishingapp.repository.CatchRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Logger;

@Service
public class AnglerService {

    private static final Logger log = Logger.getLogger(AnglerService.class.getName());

    private final AnglerRepository anglerRepository;
    private final AnglerSessionRepository anglerSessionRepository;
    private final CatchRepository catchRepository;

    public AnglerService(AnglerRepository anglerRepository, AnglerSessionRepository anglerSessionRepository, CatchRepository catchRepository) {
        this.anglerRepository = anglerRepository;
        this.anglerSessionRepository = anglerSessionRepository;
        this.catchRepository = catchRepository;
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

    public List<Angler> getAllAnglers() {
        return anglerRepository.findAll();
    }

    public Angler getAnglerById(Long id) {
        return anglerRepository.findById(id)
                .orElseThrow(() -> new AnglerNotFoundException("Angler with id " + id + " not found"));
    }

    public int getSessionCountForAngler(Long anglerId) {
        return (int) anglerSessionRepository.countByAnglerId(anglerId);
    }

    public long getAnglerCount() {
        return anglerRepository.count();
    }

    public int getCatchCountForAngler(Long anglerId) {
        return (int) catchRepository.countByAnglerId(anglerId);
    }

    /**
     * Updates an angler's name and email. Email uniqueness is enforced
     * the same way it is at creation time in findOrCreateAngler.
     */
    @Transactional
    public void updateAngler(Long anglerId, String name, String email) {
        Angler angler = getAnglerById(anglerId);

        if (!email.equalsIgnoreCase(angler.getEmail())) {
            Optional<Angler> existing = anglerRepository.findByEmailIgnoreCase(email);
            if (existing.isPresent()) {
                throw new AnglerAlreadyExistsException("An angler with that email already exists");
            }
            angler.setEmail(email);
        }

        angler.setName(name);
        anglerRepository.save(angler);
    }

    /**
     * Deletes an angler, refusing if they have any sessions or catches
     * attached — the Catch.angler column is non-nullable with no
     * cascade defined, so an unattached angler is the only kind that
     * can safely be removed without losing session/catch history.
     */
    @Transactional
    public void deleteAngler(Long anglerId) {
        Angler angler = getAnglerById(anglerId);

        if (!angler.getCatches().isEmpty() || !angler.getAnglerSessions().isEmpty()) {
            throw new AnglerHasRecordsException(
                    "Cannot delete an angler with existing sessions or catches.");
        }

        anglerRepository.delete(angler);
    }
}