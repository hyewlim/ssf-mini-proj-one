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
public class SteamReview {

    private static final Logger logger = LoggerFactory.getLogger(SteamReview.class);

    private Integer reviewScore;

    private String reviewScoreDesc;

    public static SteamReview create(JsonObject jsonObject) {
        SteamReview review = new SteamReview();
        review.setReviewScore(jsonObject.getJsonNumber("review_score").intValue());
        review.setReviewScoreDesc(jsonObject.getString("review_score_desc"));
        logger.info("Review created: " + review.toString());
        return review;
    }


}
