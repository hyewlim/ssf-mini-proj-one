package com.project.controller;

import com.project.model.SteamGame;
import com.project.model.SteamReview;
import com.project.model.SteamSpyGame;
import com.project.model.User;
import com.project.repository.GamesDao;
import com.project.service.SteamService;
import com.project.service.SteamSpyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/api")
public class ReviewRESTController {

    private static final Logger logger = LoggerFactory.getLogger(ReviewRESTController.class);

    @Autowired
    private SteamService steamService;

    @Autowired
    private SteamSpyService spyService;

    @Autowired
    private GamesDao dao;

    @GetMapping (value = "/review/{id}")
    public ResponseEntity<?> getSteamReview(@PathVariable String id) {
        SteamReview review = steamService.getReview(id);
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

    @GetMapping (value = "/url/{id}")
    public ResponseEntity<?> getUrlGame(@PathVariable String id) {
        String url = steamService.getImageUrl(id);
        return ResponseEntity.ok(url);
    }

    @GetMapping (value = "/{user}")
    public ResponseEntity<?> getUserList(@PathVariable User user) {
        List<SteamGame> list = dao.findAll(user);
        return ResponseEntity.ok(list);
    }

}
