package com.project.controller;

import com.project.model.Review;
import com.project.model.SteamSpyGame;
import com.project.service.SteamService;
import com.project.service.SteamSpyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/api")
public class ReviewRESTController {

    private static final Logger logger = LoggerFactory.getLogger(ReviewRESTController.class);

    @Autowired
    private SteamService steamService;

    @Autowired
    private SteamSpyService spyService;

    @GetMapping (value = "/review/{id}")
    public ResponseEntity<?> getSteamReview(@PathVariable String id) {
        Review review = steamService.getReview(id);
        return ResponseEntity.ok(review);
    }

    @GetMapping (value = "/getAppId")
    public ResponseEntity<?> getAppId(@RequestParam String name) {
        Integer id = steamService.getAppID(name);

        return ResponseEntity.ok(id);
    }

    @GetMapping (value = "/steamspy/{id}")
    public ResponseEntity<?> getSteamSpyGame(@PathVariable String id) {
        SteamSpyGame game = spyService.getSteamSpyGame(id);
        return ResponseEntity.ok(game);
    }

}
