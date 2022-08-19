package com.project.repository;

import com.project.model.SteamGame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Repository
public class GamesDao {

    final Logger logger = LoggerFactory.getLogger(GamesDao.class);

    public static final String HASH_KEY = "Game";

    @Autowired
    private RedisTemplate template;

    public SteamGame save(SteamGame game) {
        template.opsForHash().put(HASH_KEY, game.getTitle(), game);
        logger.info("Game saved: " + game.getTitle());
        return game;
    }

    public List<SteamGame> findAll() {
        List<SteamGame> list = template.opsForHash().values(HASH_KEY);
        return list;
    }

    public SteamGame findProductByTitle(String title){
        return (SteamGame)template.opsForHash().get(HASH_KEY, title);
    }

    public String deleteGame(String title){
        template.opsForHash().delete(HASH_KEY, title);
        logger.info("Game deleted: " + title);
        return "game removed!";
    }

}
