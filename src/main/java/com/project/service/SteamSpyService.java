package com.project.service;

import com.project.model.Review;
import com.project.model.SteamSpyGame;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.Reader;
import java.io.StringReader;

@Service
public class SteamSpyService {

    private static final Logger logger = LoggerFactory.getLogger(SteamSpyService.class);

    private static final String STEAM_SPY_URL = "https://steamspy.com/api.php";

    public SteamSpyGame getSteamSpyGame(String id) {
        String payload;

        String queryUrl = UriComponentsBuilder.fromUriString(STEAM_SPY_URL)
                .queryParam("request", "appdetails")
                .queryParam("appid", id)
                .toUriString();

        logger.info(">>> Get SteamSpy API Address: " + queryUrl);

        RequestEntity<Void> request = RequestEntity.get(queryUrl).build();

        RestTemplate template = new RestTemplate();
        ResponseEntity<String> response;

        response = template.exchange(request, String.class);
        payload = response.getBody();
        logger.info("SteamSpy payload created");

        Reader strReader = new StringReader(payload);

        JsonReader jsonReader = Json.createReader(strReader);
        JsonObject steamSpyGame = jsonReader.readObject();

        SteamSpyGame game = SteamSpyGame.create(steamSpyGame);

        return game;

    }
}
