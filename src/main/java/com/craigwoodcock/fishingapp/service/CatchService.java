package com.craigwoodcock.fishingapp.service;

import com.craigwoodcock.fishingapp.exception.AnglerNotFoundException;
import com.craigwoodcock.fishingapp.exception.CatchNotFoundException;
import com.craigwoodcock.fishingapp.model.entity.Angler;
import com.craigwoodcock.fishingapp.model.entity.Catch;
import com.craigwoodcock.fishingapp.model.entity.Session;
import com.craigwoodcock.fishingapp.repository.AnglerRepository;
import com.craigwoodcock.fishingapp.repository.CatchRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

@Service
public class CatchService {

    private static final Logger log = Logger.getLogger(CatchService.class.getName());

    private final CatchRepository catchRepository;
    private final SessionService sessionService;
    private final AnglerRepository anglerRepository;

    public CatchService(CatchRepository catchRepository, SessionService sessionService, AnglerRepository anglerRepository) {
        this.catchRepository = catchRepository;
        this.sessionService = sessionService;
        this.anglerRepository = anglerRepository;
    }

    /**
     * Records a new catch against a session, for a specific angler who is
     * already attached to that session.
     *
     * @param sessionId the session the catch belongs to
     * @param anglerId  the angler who made the catch
     * @param catchTime when the fish was caught
     * @param pegOrSwim the peg or swim number/name, may be null
     * @param fishType  the species caught
     * @param weight    the weight of the fish
     * @param notes     free-text notes, may be null
     * @param photoKey  a URL to a photo of the catch, may be null
     * @return the saved Catch entity
     */
    public Catch createCatch(Long sessionId, Long anglerId, LocalDateTime catchTime, String pegOrSwim,
                             String fishType, BigDecimal weight, String notes, String photoKey) {
        log.info("Creating new catch for session " + sessionId);

        Session session = sessionService.getSessionById(sessionId);
        Angler angler = anglerRepository.findById(anglerId)
                .orElseThrow(() -> new AnglerNotFoundException("That angler could not be found!"));

        Catch capture = new Catch();
        capture.setSession(session);
        capture.setAngler(angler);
        capture.setCatchTime(catchTime);
        capture.setPegOrSwim(pegOrSwim);
        capture.setFishType(fishType);
        capture.setWeight(weight);
        capture.setNotes(notes);
        capture.setPhotoUrl(photoKey);

        capture = catchRepository.save(capture);
        log.info("Catch created with id " + capture.getId());
        return capture;
    }

    public Catch getCatchById(Long catchId) {
        return catchRepository.findById(catchId)
                .orElseThrow(() -> new CatchNotFoundException("That catch no longer exists!"));
    }

    public List<Catch> getCatchesForSession(Long sessionId) {
        return catchRepository.findBySessionId(sessionId);
    }

    /**
     * Updates an existing catch's details, keeping its original session
     * and re-resolving the angler in case it was changed on the edit form.
     *
     * @param catchId   the id of the catch being edited
     * @param anglerId  the (possibly new) angler id
     * @param catchTime when the fish was caught
     * @param pegOrSwim the peg or swim number/name, may be null
     * @param fishType  the species caught
     * @param weight    the weight of the fish
     * @param notes     free-text notes, may be null
     * @param photoKey  a URL to a photo of the catch, may be null
     * @return the updated Catch entity
     */
    public Catch updateCatch(Long catchId, Long anglerId, LocalDateTime catchTime, String pegOrSwim,
                             String fishType, BigDecimal weight, String notes, String photoKey) {
        Catch capture = getCatchById(catchId);
        Angler angler = anglerRepository.findById(anglerId)
                .orElseThrow(() -> new AnglerNotFoundException("That angler could not be found!"));

        capture.setAngler(angler);
        capture.setCatchTime(catchTime);
        capture.setPegOrSwim(pegOrSwim);
        capture.setFishType(fishType);
        capture.setWeight(weight);
        capture.setNotes(notes);
        capture.setPhotoUrl(photoKey);

        return catchRepository.save(capture);
    }

    public void deleteCatch(Long catchId) {
        catchRepository.deleteById(catchId);
    }
}

