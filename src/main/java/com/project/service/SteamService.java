package com.project.service;

import com.project.model.SteamGame;
import com.project.model.SteamReview;
import com.project.model.SteamSpyGame;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.*;
import java.util.*;

@Service
public class SteamService {

    private static final Logger logger = LoggerFactory.getLogger(SteamService.class);
    private static final String URL = "https://store.steampowered.com/appreviews/";
    private static final String URL_GET_APP_ID = "https://api.steampowered.com/ISteamApps/GetAppList/v2";
    private static final String URL_STEAM_IMAGE = "https://store.steampowered.com/api/appdetails";
    private static final String URL_PAGINATE = "https://steamcommunity.com/actions/SearchApps/";

    public SteamReview getReview(String id) {
        String payload;

        String queryUrl = UriComponentsBuilder.fromUriString(URL)
                .path(id)
                .queryParam("json", 1)
                .toUriString();

        logger.info(">>> Get Review Address: " + queryUrl);

        RequestEntity<Void> request = RequestEntity.get(queryUrl).build();

        RestTemplate template = new RestTemplate();
        ResponseEntity<String> response;

        response = template.exchange(request, String.class);
        payload = response.getBody();
        logger.info("Steam payload created");

        Reader strReader = new StringReader(payload);

        JsonReader jsonReader = Json.createReader(strReader);
        JsonObject reviews = jsonReader.readObject().getJsonObject("query_summary");

        SteamReview review = SteamReview.create(reviews);

        return review;
    }

    public Integer getAppID(String name) {
        JsonArray apps = getJsonPayload();
        Integer appId = 0;
        for (int i = 0; i < apps.size(); i++) {
            JsonObject app = apps.getJsonObject(i);
            if (app.getString("name").equalsIgnoreCase(name)) {
                appId = app.getInt("appid");
            }
        }
        return appId;
    }

    public List<String> getPaginatedSteamList(String query) {

        String payload;

        String queryUrl = UriComponentsBuilder.fromUriString(URL_PAGINATE)
                .path(query)
                .toUriString();

        logger.info(">>> Paginate Address: " + queryUrl);

        RequestEntity<Void> request = RequestEntity.get(queryUrl).build();

        RestTemplate template = new RestTemplate();
        ResponseEntity<String> response;

        response = template.exchange(request, String.class);
        payload = response.getBody();

        Reader strReader = new StringReader(payload);

        List<String> list = new ArrayList<>();
        JsonReader jsonReader = Json.createReader(strReader);
        JsonArray result = jsonReader.readArray();
        for (int i = 0; i < result.size(); i++) {
            JsonObject gameResponse = result.getJsonObject(i);
            String gameTitle = gameResponse.getString("name");
            list.add(gameTitle);
        }
        logger.info(list.toString());
        return list;

    }

    public String getImageUrl(String id) {
        String payload;

        String queryUrl = UriComponentsBuilder.fromUriString(URL_STEAM_IMAGE)
                .queryParam("appids", id)
                .toUriString();

        logger.info(">>> Get URL Address: " + queryUrl);

        RequestEntity<Void> request = RequestEntity.get(queryUrl).build();

        RestTemplate template = new RestTemplate();
        ResponseEntity<String> response;

        response = template.exchange(request, String.class);
        payload = response.getBody();
        logger.info("URL Payload: created");

        Reader strReader = new StringReader(payload);

        JsonReader jsonReader = Json.createReader(strReader);
        String imageUrl = jsonReader.readObject().getJsonObject(id).getJsonObject("data").getString("header_image");
        logger.info(imageUrl.toString());

        return imageUrl;
    }


    public static SteamGame createMain(SteamGame game, SteamSpyGame spyGame, SteamReview review) {
        SteamService steamService = new SteamService();
        SteamSpyService steamSpyService = new SteamSpyService();
        game.setAppId(steamService.getAppID(game.getTitle()));
        game.setAverageTimeSpent(spyGame.getAverageTimeSpent());
        game.setMedianTimeSpent(spyGame.getMedianTimeSpent());
        game.setReviewScore(review.getReviewScore());
        game.setReviewScoreDesc(review.getReviewScoreDesc());
        game.setImageUrl(steamService.getImageUrl(String.valueOf(game.getAppId())));
        logger.info("Main game created: " + game.toString());
        return game;
    }

    public JsonArray getJsonPayload() {
        String payload;

        RequestEntity<Void> request = RequestEntity.get(URL_GET_APP_ID).build();

        RestTemplate template = new RestTemplate();
        ResponseEntity<String> response;

        response = template.exchange(request, String.class);
        payload = response.getBody();
        logger.info("Payload: created");
        Reader strReader = new StringReader(payload);

        JsonReader jsonReader = Json.createReader(strReader);
        JsonObject applist = jsonReader.readObject().getJsonObject("applist");
        JsonArray apps = applist.getJsonArray("apps");
        return apps;
    }



}