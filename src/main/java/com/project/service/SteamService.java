package com.project.service;

import com.project.model.Review;
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

import java.io.Reader;
import java.io.StringReader;

@Service
public class SteamService {

    private static final Logger logger = LoggerFactory.getLogger(SteamService.class);

    private static final String URL = "https://store.steampowered.com/appreviews/";

    private static final String URL_GET_APP_ID = "https://api.steampowered.com/ISteamApps/GetAppList/v2";

    private static final String URL_RECENTLY_PLAYED_GAMES = "http://api.steampowered.com/IPlayerService/GetRecentlyPlayedGames/v0001/";

    public Review getReview(String id) {
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

        Review review = Review.create(reviews);

        return review;
    }

    public Integer getAppID(String name) {
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

        Integer appId = 0;

        for (int i = 0; i < apps.size(); i++) {

            JsonObject app = apps.getJsonObject(i);
            if (app.getString("name").equalsIgnoreCase(name)) {
                appId = app.getInt("appid");
            }

        }

        return appId;

    }


}
