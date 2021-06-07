package com.openclassrooms.watchlist.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import com.openclassrooms.watchlist.exception.DuplicateTitleException;
import com.openclassrooms.watchlist.model.WatchlistItem;
import com.openclassrooms.watchlist.service.WatchlistService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class WatchlistController {

	Logger logger = LoggerFactory.getLogger(WatchlistController.class);

	@Autowired
    private WatchlistService watchlistService;

	// @Autowired
	// public WatchlistController(WatchlistService watchlistService) {
	// 	super();
	// 	this.watchlistService = watchlistService;
	// }
	
    @GetMapping("/watchlist")
    public ModelAndView getWatchlist(@AuthenticationPrincipal OAuth2User user){
		
		logger.info("GET /watchlist called");

        String viewName = "watchlist";

        Map<String, Object> model = new HashMap<String, Object>();
        
        
        model.put("watchlistItems", watchlistService.getWatchlistItems());
        model.put("numberOfMovies", watchlistService.getWatchlistItems().size());
		model.put("user", user.getName());

        return new ModelAndView(viewName, model);
    }

    @GetMapping("/watchlistItemForm")
	public ModelAndView showWatchlistItemForm(@RequestParam(required=false) String id) {
		
		String viewName = "watchlistItemForm";
		
		Map<String,Object> model = new HashMap<String,Object>();

		if(id == null){
			model.put("watchlistItem", new WatchlistItem());
			return new ModelAndView(viewName,model); 
		}
		
		Optional<WatchlistItem> watchlistItem = watchlistService.findWatchlistItemById(id);
		if (watchlistItem.isEmpty()) {
			model.put("watchlistItem", new WatchlistItem());
		} else {
			model.put("watchlistItem", watchlistItem);
		}
		
		return new ModelAndView(viewName,model); 
    }
    
    @PostMapping("/watchlistItemForm")
	public ModelAndView submitWatchlistItemForm(@Valid WatchlistItem watchlistItem, BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
			return new ModelAndView("watchlistItemForm");
		}
		
		try {
			watchlistService.addOrUpdateWatchlistItem(watchlistItem);
		} catch (DuplicateTitleException e) {
			bindingResult.rejectValue("title", "", "This title already exists on your watchlist");
			return new ModelAndView("watchlistItemForm");
		}
		
		RedirectView redirect = new RedirectView();
		redirect.setUrl("/watchlist");
		
		return new ModelAndView(redirect);
    }

}
