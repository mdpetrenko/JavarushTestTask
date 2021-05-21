package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;

import java.util.List;

public interface PlayerService {

    Player findById(Long id);

    List<Player> findAll(String name, String title, Race race, Profession profession,
                         Long after, Long before, Boolean banned, Long minExperience,
                         Long maxExperience, Long minLevel, Long maxLevel, PlayerOrder order,
                         Integer pageNumber, Integer pageSize);

    Long count(String name, String title, Race race, Profession profession, Long after, Long before,
                  Boolean banned, Long minExperience, Long maxExperience, Long minLevel,
                  Long maxLevel);

    void delete(Long id);

    Player saveOrUpdate(Player player);
}
