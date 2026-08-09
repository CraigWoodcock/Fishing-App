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
import org.springframework.security.access.AccessDeniedException;
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
 * Provides functionality for creating, viewing, updating, and deleting fishing sessions,
 * and for managing which anglers are attached to a session.
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
     * Displays the form for creating a new fishing session. Anglers are no
     * longer entered here; they're added one at a time from the session
     * view once the session exists.
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
     * Creates a new fishing session. The creating user is automatically
     * attached as an angler on the session.
     *
     * @param venue              The fishing venue name
     * @param startDate          The start date of the session
     * @param durationHours      Duration of the session in hours
     * @param authentication     Current user's authentication
     * @param redirectAttributes Redirect attributes for flash messages
     * @return Redirect to the newly created session
     */
    @PostMapping("/create")
    public String createSession(@RequestParam String venue,
                                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                @RequestParam int durationHours,
                                Authentication authentication,
                                RedirectAttributes redirectAttributes) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        log.info("User Retrieved = " + user.getName());
        Session session = sessionService.createSession(user, venue, startDate, durationHours);
        redirectAttributes.addFlashAttribute("message", "Session Created");

        return "redirect:/sessions/" + session.getId();
    }

    /**
     * Displays a session. Viewable by the owner and by any angler on the
     * session whose email matches their own registered account.
     */
    @GetMapping("/{id}")
    public String viewSession(@PathVariable Long id, Model model, Authentication authentication) {
        Session session = sessionService.getSessionById(id);
        User currentUser = userService.findByUsername(authentication.getName());

        if (!sessionService.isUserAssociatedWithSession(session, currentUser)) {
            throw new AccessDeniedException("You do not have permission to view this session.");
        }

        model.addAttribute("sess", session);
        model.addAttribute("isOwner", sessionService.isOwner(session, currentUser));
        model.addAttribute("catches", catchService.getCatchesForSession(id));
        model.addAttribute("s3Service", s3Service);
        return "view-session";
    }

    /**
     * Displays the form for editing an existing session's details and
     * managing its anglers. Owner only.
     */
    @GetMapping("/{id}/edit")
    public String editSessionForm(@PathVariable Long id, Model model, Authentication authentication) {
        Session session = sessionService.getSessionById(id);
        requireOwnership(session, authentication);

        model.addAttribute("sess", session);
        model.addAttribute("anglers", sessionService.getAnglersForSession(id));
        return "edit-session";
    }

    /**
     * Applies edits to a session's venue, start date, and duration.
     * Angler management is handled separately via the add/remove-angler
     * endpoints below. Owner only.
     */
    @PostMapping("/{id}/edit")
    public String updateSession(@PathVariable Long id,
                                @RequestParam String venue,
                                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                @RequestParam int durationHours,
                                Authentication authentication,
                                RedirectAttributes redirectAttributes) {
        Session session = sessionService.getSessionById(id);
        requireOwnership(session, authentication);

        sessionService.updateSessionDetails(id, venue, startDate, durationHours);
        redirectAttributes.addFlashAttribute("message", "Session Updated");
        return "redirect:/sessions/" + id;
    }

    /**
     * Displays the form for adding a single angler to a session, capturing
     * their name and an optional email. If the email matches a registered
     * user's account, that user will be able to view this session and
     * their own catches within it. Owner only.
     */
    @GetMapping("/{id}/anglers/new")
    public String newAnglerForm(@PathVariable Long id, Model model, Authentication authentication) {
        Session session = sessionService.getSessionById(id);
        requireOwnership(session, authentication);

        model.addAttribute("sess", session);
        return "new-angler";
    }

    /**
     * Adds a single angler to a session. Owner only.
     */
    @PostMapping("/{id}/anglers")
    public String addAngler(@PathVariable Long id,
                            @RequestParam String name,
                            @RequestParam String email,
                            Authentication authentication,
                            RedirectAttributes redirectAttributes) {
        Session session = sessionService.getSessionById(id);
        requireOwnership(session, authentication);

        sessionService.addAnglerToSession(id, name, email);
        redirectAttributes.addFlashAttribute("message", "Angler Added");
        return "redirect:/sessions/" + id;
    }

    /**
     * Attempts to remove an angler from a session. If they have no logged
     * catches they're removed immediately; otherwise the user is shown a
     * confirmation page to choose between reassigning or deleting those
     * catches before the angler can be removed. Owner only.
     */
    @PostMapping("/{sessionId}/anglers/{anglerId}/remove-check")
    public String checkRemoveAngler(@PathVariable Long sessionId, @PathVariable Long anglerId,
                                    Model model, Authentication authentication,
                                    RedirectAttributes redirectAttributes) {
        Session session = sessionService.getSessionById(sessionId);
        requireOwnership(session, authentication);

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

        model.addAttribute("sess", session);
        model.addAttribute("angler", angler);
        model.addAttribute("catchCount", catches.size());
        model.addAttribute("otherAnglers", otherAnglers);
        return "remove-angler-confirm";
    }

    /**
     * Finalises the removal of an angler who had logged catches, either
     * reassigning those catches to another angler on the session or
     * deleting them, per the user's choice on the confirmation page.
     * Owner only.
     */
    @PostMapping("/{sessionId}/anglers/{anglerId}/remove")
    public String removeAngler(@PathVariable Long sessionId, @PathVariable Long anglerId,
                               @RequestParam String action,
                               @RequestParam(required = false) Long reassignToAnglerId,
                               Authentication authentication,
                               RedirectAttributes redirectAttributes) {
        Session session = sessionService.getSessionById(sessionId);
        requireOwnership(session, authentication);

        if ("reassign".equals(action)) {
            catchService.reassignCatches(sessionId, anglerId, reassignToAnglerId);
        } else {
            catchService.deleteCatchesForAnglerInSession(sessionId, anglerId);
        }
        sessionService.removeAnglerFromSession(sessionId, anglerId);
        redirectAttributes.addFlashAttribute("message", "Angler removed from session");
        return "redirect:/sessions/" + sessionId + "/edit";
    }

    /**
     * Deletes a session entirely. Owner only.
     */
    @PostMapping("/{id}/delete")
    public String deleteSession(@PathVariable Long id, Authentication authentication,
                                RedirectAttributes redirectAttributes) {
        Session session = sessionService.getSessionById(id);
        requireOwnership(session, authentication);

        sessionService.deleteSession(id);
        redirectAttributes.addFlashAttribute("message", "Session Deleted Successfully");
        return "redirect:/dashboard";
    }

    /**
     * Verifies the currently authenticated user owns the given session,
     * throwing so Spring Security's access-denied handling returns a 403
     * if they don't. Used to guard every session-modifying endpoint above.
     *
     * @param session        the session being acted on
     * @param authentication the current request's authentication
     */
    private void requireOwnership(Session session, Authentication authentication) {
        User currentUser = userService.findByUsername(authentication.getName());
        if (!sessionService.isOwner(session, currentUser)) {
            throw new AccessDeniedException("You do not have permission to modify this session.");
        }
    }
}