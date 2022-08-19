package com.project.model;

import com.project.service.SteamService;
import jakarta.json.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {

    private static final Logger logger = LoggerFactory.getLogger(Review.class);

    private Integer reviewScore;

    private String reviewScoreDesc;

    public static Review create(JsonObject jsonObject) {
        Review review = new Review();
        review.setReviewScore(jsonObject.getJsonNumber("review_score").intValue());
        review.setReviewScoreDesc(jsonObject.getString("review_score_desc"));
        logger.info("Review created: " + review.toString());
        return review;
    }


}
