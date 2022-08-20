package com.project.controller;


import com.project.model.SteamGame;
import com.project.model.SteamReview;
import com.project.model.SteamSpyGame;
import com.project.repository.GamesDao;
import com.project.service.SteamService;
import com.project.service.SteamSpyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping
public class IndexController {

    @Autowired
    private SteamService service;

    @Autowired
    private GamesDao dao;

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
        dao.save(game);
        model.addAttribute("game", game);
        return "add-game-success";
    }

    @GetMapping("/list")
    public String listGames(Model model) {
        List<SteamGame> listOfGames = dao.findAll();
        model.addAttribute("listOfGames", listOfGames);
        return "list-games";
    }

    @GetMapping("/update")
    public String updateGame(@RequestParam("gameTitle") String title, Model model) {
        SteamGame game = dao.findProductByTitle(title);
        model.addAttribute("game", game);

        List<String> listStatus = Arrays.asList("Playing", "Backlog", "Completed", "Retired");
        model.addAttribute("listStatus", listStatus);

        List<Integer> listRating = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        model.addAttribute("listRating", listRating);
        return "add-game";
    }

    @GetMapping("/delete")
    public String deleteGame(@RequestParam("gameTitle") String title) {
        dao.deleteGame(title);
        return "list-games";
    }

}
