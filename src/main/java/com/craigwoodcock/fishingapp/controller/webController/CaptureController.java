package com.craigwoodcock.fishingapp.controller.webController;

import com.craigwoodcock.fishingapp.model.entity.Angler;
import com.craigwoodcock.fishingapp.model.entity.Catch;
import com.craigwoodcock.fishingapp.model.entity.Session;
import com.craigwoodcock.fishingapp.service.CatchService;
import com.craigwoodcock.fishingapp.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

/**
 * Controller handling the recording, editing, and deletion of fish catches
 * against an existing fishing session.
 *
 * @author Craig Woodcock
 * @version 1.0
 */
@Controller
@RequestMapping("/sessions/{sessionId}/catches")
public class CaptureController {

    private static final Logger log = Logger.getLogger(CaptureController.class.getName());
    private final CatchService catchService;
    private final SessionService sessionService;

    @Autowired
    public CaptureController(CatchService catchService, SessionService sessionService) {
        this.catchService = catchService;
        this.sessionService = sessionService;
    }

    /**
     * Displays the form for logging a new catch against a session.
     * The angler dropdown is populated only with anglers already attached
     * to this session, so a catch can never be logged for someone who
     * wasn't on the trip.
     */
    @GetMapping("/new")
    public String newCatchForm(@PathVariable Long sessionId, Model model) {
        Session session = sessionService.getSessionById(sessionId);
        List<Angler> anglers = sessionService.getAnglersForSession(sessionId);

        model.addAttribute("session", session);
        model.addAttribute("anglers", anglers);
        model.addAttribute("catchRecord", new Catch());
        return "catches/new-catch";
    }

    /**
     * Creates a new catch record for the session.
     */
    @PostMapping
    public String createCatch(@PathVariable Long sessionId,
                              @RequestParam Long anglerId,
                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime catchTime,
                              @RequestParam(required = false) String pegOrSwim,
                              @RequestParam String fishType,
                              @RequestParam BigDecimal weight,
                              @RequestParam(required = false) String notes,
                              @RequestParam(required = false) String photoUrl,
                              RedirectAttributes redirectAttributes) {
        catchService.createCatch(sessionId, anglerId, catchTime, pegOrSwim, fishType, weight, notes, photoUrl);
        redirectAttributes.addFlashAttribute("message", "Catch Logged");
        return "redirect:/sessions/" + sessionId;
    }

    /**
     * Displays the form for editing an existing catch.
     */
    @GetMapping("/{catchId}/edit")
    public String editCatchForm(@PathVariable Long sessionId, @PathVariable Long catchId, Model model) {
        Session session = sessionService.getSessionById(sessionId);
        Catch catchRecord = catchService.getCatchById(catchId);
        List<Angler> anglers = sessionService.getAnglersForSession(sessionId);

        model.addAttribute("session", session);
        model.addAttribute("catchRecord", catchRecord);
        model.addAttribute("anglers", anglers);
        return "catches/edit-catch";
    }

    /**
     * Applies edits to an existing catch record.
     */
    @PostMapping("/{catchId}")
    public String updateCatch(@PathVariable Long sessionId,
                              @PathVariable Long catchId,
                              @RequestParam Long anglerId,
                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime catchTime,
                              @RequestParam(required = false) String pegOrSwim,
                              @RequestParam String fishType,
                              @RequestParam BigDecimal weight,
                              @RequestParam(required = false) String notes,
                              @RequestParam(required = false) String photoUrl,
                              RedirectAttributes redirectAttributes) {
        catchService.updateCatch(catchId, anglerId, catchTime, pegOrSwim, fishType, weight, notes, photoUrl);
        redirectAttributes.addFlashAttribute("message", "Catch Updated");
        return "redirect:/sessions/" + sessionId;
    }

    /**
     * Deletes a catch record from a session.
     */
    @PostMapping("/{catchId}/delete")
    public String deleteCatch(@PathVariable Long sessionId, @PathVariable Long catchId,
                              RedirectAttributes redirectAttributes) {
        catchService.deleteCatch(catchId);
        redirectAttributes.addFlashAttribute("message", "Catch Removed");
        return "redirect:/sessions/" + sessionId;
    }
}