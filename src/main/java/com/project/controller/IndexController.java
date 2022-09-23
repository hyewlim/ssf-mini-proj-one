package com.project.controller;


import com.project.model.SteamGame;
import com.project.model.User;
import com.project.repository.GamesDao;
import com.project.service.SteamService;
import com.project.service.SteamSpyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping
public class IndexController {

    final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private SteamService service;

    @Autowired
    private GamesDao dao;

    @Autowired
    private User user;

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String userSubmit(@ModelAttribute User user, Model model){
        this.user = user;
        model.addAttribute("user", user);
        List<SteamGame> listOfGames = dao.findAll(user);
        model.addAttribute("listOfGames", listOfGames);
        return "list-games";
    }

    @GetMapping("/create")
    public String createGame(Model model){
        SteamGame game = new SteamGame();
        model.addAttribute("game", game);

        List<String> listStatus = Arrays.asList("Playing", "Backlog", "Completed", "Retired");
        model.addAttribute("listStatus", listStatus);

        List<Integer> listRating = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        model.addAttribute("listRating", listRating);

        return "add-game";

    }

    @PostMapping("/create")
    public String postGame(@ModelAttribute("game") SteamGame sgame, Model model) {
        SteamGame game = SteamService.createMain(sgame,
                new SteamSpyService().getSteamSpyGame(String.valueOf(service.getAppID(sgame.getTitle()))),
                new SteamService().getReview(String.valueOf(service.getAppID(sgame.getTitle()))));
        dao.save(game, user);
        model.addAttribute("game", game);
        return "add-game-success";
    }

    @GetMapping("/list")
    public String listGames(Model model) {
        List<SteamGame> listOfGames = dao.findAll(user);
        model.addAttribute("user", user);
        model.addAttribute("listOfGames", listOfGames);
        return "list-games";
    }

    @GetMapping("/update")
    public String updateGame(@RequestParam("gameTitle") String title, Model model) {
        SteamGame game = dao.findProductByTitle(title, user);
        model.addAttribute("game", game);

        List<String> listStatus = Arrays.asList("Playing", "Backlog", "Completed", "Retired");
        model.addAttribute("listStatus", listStatus);

        List<Integer> listRating = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        model.addAttribute("listRating", listRating);
        return "add-game";
    }

    @GetMapping("/delete")
    public String deleteGame(@RequestParam("gameTitle") String title, Model model) {
        dao.deleteGame(title, user);
        List<SteamGame> listOfGames = dao.findAll(user);
        model.addAttribute("user", user);
        model.addAttribute("listOfGames", listOfGames);
        return "list-games";
    }

    @GetMapping("/stats")
    public String stats(Model model){
        List<SteamGame> listOfGames = dao.findAll(user);

        int hoursPlayed = 0;
        for (int i = 0; i < listOfGames.size(); i++) {
            hoursPlayed += listOfGames.get(i).getHoursPlayed();
        }

        int gamesCurrentlyPlaying = 0;
        for (int i = 0; i < listOfGames.size(); i++) {
            if (listOfGames.get(i).getStatus().equalsIgnoreCase("Playing")) {
                gamesCurrentlyPlaying ++;
            }
        }

        model.addAttribute("gcp", gamesCurrentlyPlaying);
        model.addAttribute("hrplay", hoursPlayed);
        model.addAttribute("user", user);
        model.addAttribute("listOfGames", listOfGames);
        return "stats";
    }

}
