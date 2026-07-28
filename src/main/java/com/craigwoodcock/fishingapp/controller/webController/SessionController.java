package com.craigwoodcock.fishingapp.controller.webController;

import com.craigwoodcock.fishingapp.exception.AnglerNotFoundException;
import com.craigwoodcock.fishingapp.model.entity.Angler;
import com.craigwoodcock.fishingapp.model.entity.Catch;
import com.craigwoodcock.fishingapp.model.entity.Session;
import com.craigwoodcock.fishingapp.model.entity.User;
import com.craigwoodcock.fishingapp.repository.AnglerRepository;
import com.craigwoodcock.fishingapp.service.CatchService;
import com.craigwoodcock.fishingapp.service.S3Service;
import com.craigwoodcock.fishingapp.service.SessionService;
import com.craigwoodcock.fishingapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

/**
 * Controller handling fishing session management operations for the web interface.
 * Provides functionality for creating, viewing, updating, and deleting fishing sessions.
 *
 * @author Craig Woodcock
 * @version 1.0
 */
@Controller
@RequestMapping("/sessions")
public class SessionController {

    private static final Logger log = Logger.getLogger(SessionController.class.getName());
    private final SessionService sessionService;
    private final UserService userService;
    private final AnglerRepository anglerRepository;
    private final CatchService catchService;
    private final S3Service s3Service;

    @Autowired
    public SessionController(SessionService sessionService, UserService userService, AnglerRepository anglerRepository, CatchService catchService, S3Service s3Service) {
        this.sessionService = sessionService;
        this.userService = userService;
        this.anglerRepository = anglerRepository;
        this.catchService = catchService;
        this.s3Service = s3Service;
    }

    /**
     * Displays the form for creating a new fishing session.
     *
     * @param model The Spring MVC model
     * @return The new session form view
     */
    @GetMapping("/new")
    public String newSessionForm(Model model) {
        model.addAttribute("session", new Session());
        return "new-session";
    }

    /**
     * Creates a new fishing session.
     *
     * @param venue              The fishing venue name
     * @param startDate          The start date of the session
     * @param durationHours      Duration of the session in hours
     * @param anglersList        List of anglers participating
     * @param authentication     Current user's authentication
     * @param redirectAttributes Redirect attributes for flash messages
     * @return Redirect to dashboard
     */
    @PostMapping("/create")
    public String createSession(@RequestParam String venue,
                                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                @RequestParam int durationHours,
                                @RequestParam String anglersList,
                                Authentication authentication,
                                RedirectAttributes redirectAttributes) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        log.info("User Retrieved = " + user.getName());
        sessionService.createSession(user, venue, startDate, durationHours, anglersList);
        redirectAttributes.addFlashAttribute("message", "Session Created");

        return "redirect:/dashboard";
    }

    @GetMapping("/{id}")
    public String viewSession(@PathVariable Long id, Model model) {
        Session session = sessionService.getSessionById(id);
        model.addAttribute("sess", session);
        model.addAttribute("catches", catchService.getCatchesForSession(id));
        model.addAttribute("s3Service", s3Service);
        return "view-session";
    }

    /**
     * Displays the form for editing an existing session's details and
     * managing its anglers.
     */
    @GetMapping("/{id}/edit")
    public String editSessionForm(@PathVariable Long id, Model model) {
        Session session = sessionService.getSessionById(id);
        model.addAttribute("sess", session);
        model.addAttribute("anglers", sessionService.getAnglersForSession(id));
        return "edit-session";
    }

    /**
     * Applies edits to a session's venue, start date, duration, and adds
     * any newly-listed anglers, then redirects back to the session view.
     */
    @PostMapping("/{id}/edit")
    public String updateSession(@PathVariable Long id,
                                @RequestParam String venue,
                                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                @RequestParam int durationHours,
                                @RequestParam(required = false) String anglersList,
                                RedirectAttributes redirectAttributes) {
        sessionService.updateSessionDetails(id, venue, startDate, durationHours);
        sessionService.addAnglersToSession(id, anglersList);
        redirectAttributes.addFlashAttribute("message", "Session Updated");
        return "redirect:/sessions/" + id;
    }

    /**
     * Attempts to remove an angler from a session. If they have no logged
     * catches they're removed immediately; otherwise the user is shown a
     * confirmation page to choose between reassigning or deleting those
     * catches before the angler can be removed.
     */
    @PostMapping("/{sessionId}/anglers/{anglerId}/remove-check")
    public String checkRemoveAngler(@PathVariable Long sessionId, @PathVariable Long anglerId,
                                    Model model, RedirectAttributes redirectAttributes) {
        List<Catch> catches = catchService.getCatchesForSession(sessionId).stream()
                .filter(c -> c.getAngler().getId().equals(anglerId))
                .toList();

        if (catches.isEmpty()) {
            sessionService.removeAnglerFromSession(sessionId, anglerId);
            redirectAttributes.addFlashAttribute("message", "Angler removed from session");
            return "redirect:/sessions/" + sessionId + "/edit";
        }

        Angler angler = anglerRepository.findById(anglerId)
                .orElseThrow(() -> new AnglerNotFoundException("That angler could not be found!"));
        List<Angler> otherAnglers = sessionService.getAnglersForSession(sessionId).stream()
                .filter(a -> !a.getId().equals(anglerId))
                .toList();

        model.addAttribute("sess", sessionService.getSessionById(sessionId));
        model.addAttribute("angler", angler);
        model.addAttribute("catchCount", catches.size());
        model.addAttribute("otherAnglers", otherAnglers);
        return "remove-angler-confirm";
    }

    /**
     * Finalises the removal of an angler who had logged catches, either
     * reassigning those catches to another angler on the session or
     * deleting them, per the user's choice on the confirmation page.
     */
    @PostMapping("/{sessionId}/anglers/{anglerId}/remove")
    public String removeAngler(@PathVariable Long sessionId, @PathVariable Long anglerId,
                               @RequestParam String action,
                               @RequestParam(required = false) Long reassignToAnglerId,
                               RedirectAttributes redirectAttributes) {
        if ("reassign".equals(action)) {
            catchService.reassignCatches(sessionId, anglerId, reassignToAnglerId);
        } else {
            catchService.deleteCatchesForAnglerInSession(sessionId, anglerId);
        }
        sessionService.removeAnglerFromSession(sessionId, anglerId);
        redirectAttributes.addFlashAttribute("message", "Angler removed from session");
        return "redirect:/sessions/" + sessionId + "/edit";
    }

    @PostMapping("/{id}/delete")
    public String deleteSession(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        sessionService.deleteSession(id);
        redirectAttributes.addFlashAttribute("message", "Session Deleted Successfully");
        return "redirect:/dashboard";
    }


}