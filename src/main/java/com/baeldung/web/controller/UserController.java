package com.baeldung.web.controller;

import java.util.Locale;

import com.baeldung.persistence.model.User;
import com.baeldung.security.ActiveUserStore;
import com.baeldung.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @Autowired
    ActiveUserStore activeUserStore;

    @Autowired
    IUserService userService;

    @GetMapping("/loggedUsers")
    public String getLoggedUsers(final Locale locale, final Model model) {
        model.addAttribute("users", activeUserStore.getUsers());
        return "users";
    }

    @GetMapping("/loggedUsersFromSessionRegistry")
    public String getLoggedUsersFromSessionRegistry(final Locale locale, final Model model) {
        model.addAttribute("users", userService.getUsersFromSessionRegistry());
        return "users";
    }

    // To inject the logged user, see https://stackoverflow.com/questions/31159075/
    @GetMapping("/homepage")
    public String getHomePage(final Locale locale, final Model model, @AuthenticationPrincipal User user) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String user_details = user != null && user.isEnabled() ? user.getEmail() : null;
        model.addAttribute("user_details", user_details);

        return "homepage.html";
    }
}
