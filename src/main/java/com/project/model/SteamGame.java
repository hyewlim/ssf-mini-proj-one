package com.project.model;

import jakarta.json.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class SteamGame implements Serializable {

    private Integer appId;
    private String title;
    private Integer rating;
    private String status;
    private Integer hoursPlayed;
    private Integer averageTimeSpent;
    private Integer medianTimeSpent;
    private Integer reviewScore;
    private String reviewScoreDesc;
    private String imageUrl;




}
