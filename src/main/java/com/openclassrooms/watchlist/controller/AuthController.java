package com.openclassrooms.watchlist.controller;

import java.util.HashMap;
import java.util.Map;

import com.openclassrooms.watchlist.service.WatchlistService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class AuthController {

    @Autowired
    private WatchlistService watchlistService;

    @GetMapping("/")
    public ModelAndView home(@AuthenticationPrincipal OidcUser user) {
        // return "Welcome, "+ user.getFullName() + "!";

        String viewName = "home";

        Map<String, Object> model = new HashMap<String, Object>();

        ModelAndView modelAndView = new ModelAndView(viewName, model);

        return modelAndView;
    }
}
