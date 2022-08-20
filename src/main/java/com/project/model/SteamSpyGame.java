package com.project.model;

import jakarta.json.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SteamSpyGame {

    private static final Logger logger = LoggerFactory.getLogger(SteamSpyGame.class);

    private Integer appId;
    private String name;
    private Integer averageTimeSpent;
    private Integer medianTimeSpent;

    public static SteamSpyGame create(JsonObject jsonObject) {
        SteamSpyGame game = new SteamSpyGame();
        game.setAppId(jsonObject.getJsonNumber("appid").intValue());
        game.setName(jsonObject.getString("name"));
        game.setAverageTimeSpent(jsonObject.getJsonNumber("average_forever").intValue() / 60);
        game.setMedianTimeSpent(jsonObject.getJsonNumber("median_forever").intValue() / 60);
        logger.info("SteamSpyGame created: " + game.toString());
        return game;
    }
}
