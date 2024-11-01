package com.craigwoodcock.fishingapp.webController;

import com.craigwoodcock.fishingapp.model.Session;
import com.craigwoodcock.fishingapp.model.User;
import com.craigwoodcock.fishingapp.repository.AnglerRepository;
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
import java.util.logging.Logger;

@Controller
@RequestMapping("/sessions")
public class SessionController {

    private static final Logger log = Logger.getLogger(SessionController.class.getName());
    private final SessionService sessionService;
    private final UserService userService;
    private final AnglerRepository anglerRepository;

    @Autowired
    public SessionController(SessionService sessionService, UserService userService, AnglerRepository anglerRepository) {
        this.sessionService = sessionService;
        this.userService = userService;
        this.anglerRepository = anglerRepository;
    }

    @GetMapping("/new")
    public String newSessionForm(Model model) {
        model.addAttribute("session", new Session());
        return "new-session";
    }


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
        Session session = sessionService.getSessionById(id)
                .orElseThrow(() -> new RuntimeException("Session not found"));
        model.addAttribute("sess", session);
        return "view-session";
    }

    @GetMapping("/{id}/edit")
    public String editSessionForm(@PathVariable Long id, Model model) {
        Session session = sessionService.getSessionById(id)
                .orElseThrow(() -> new RuntimeException("Session not found"));
        model.addAttribute("sessions", session);
        return "sessions/form";
    }

    @PostMapping("/{id}")
    public String updateSession(@PathVariable Long id, @ModelAttribute Session session) {
        session.setId(id);
        sessionService.updateSession(session);
        return "redirect:/dashboard";
    }

    @PostMapping("/{id}/delete")
    public String deleteSession(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        sessionService.deleteSession(id);
        redirectAttributes.addFlashAttribute("message", "Session Deleted Successfully");
        return "redirect:/dashboard";
    }

//    @PostMapping("/{id}/anglers")
//    public String addAngler(@PathVariable Long id, @ModelAttribute Angler angler) {
//        sessionService.addAnglerToSession(id, angler);
//        return "redirect:/sessions/" + id;
//    }

    @PostMapping("/{sessionId}/anglers/{anglerId}/delete")
    public String removeAngler(@PathVariable Long sessionId, @PathVariable Long anglerId) {
        sessionService.removeAnglerFromSession(sessionId, anglerId);
        return "redirect:/sessions/" + sessionId;
    }

//    @PostMapping("/{sessionId}/anglers/{anglerId}/catches")
//    public String addCatch(@PathVariable Long sessionId, @PathVariable Long anglerId, @ModelAttribute Catch capture) {
//        sessionService.addCatchToSession(sessionId, anglerId, capture);
//        return "redirect:/sessions/" + sessionId;
//    }

//    @PostMapping("/{sessionId}/catches/{catchId}/delete")
//    public String removeCatch(@PathVariable Long sessionId, @PathVariable Long catchId) {
//        sessionService.removeCatchFromSession(sessionId, catchId);
//        return "redirect:/sessions/" + sessionId;
//    }
}