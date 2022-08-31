package com.project.repository;

import com.project.model.SteamGame;
import com.project.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class GamesDao {

    final Logger logger = LoggerFactory.getLogger(GamesDao.class);

    public static final String HASH_KEY = "Game";

    @Autowired
    private RedisTemplate template;

    public SteamGame save(SteamGame game, User user) {
        template.opsForHash().put(user.getUserName(), game.getTitle(), game);
        logger.info("Game saved: " + game.getTitle());
        logger.info("User: " + user.getUserName());
        return game;
    }

    public List<SteamGame> findAll(User user) {
        List<SteamGame> list = template.opsForHash().values(user.getUserName());
        return list;
    }

    public SteamGame findProductByTitle(String title, User user){
        return (SteamGame)template.opsForHash().get(user.getUserName(), title);
    }

    public String deleteGame(String title, User user){
        template.opsForHash().delete(user.getUserName(), title);
        logger.info("Game deleted: " + title);
        return "game removed!";
    }

}
