package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.repository.PlayerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Player findById(Long id) {
        if (id < 1) throw new NumberFormatException("Not a valid id. Must be Integer > 0");
        return playerRepository.findById(id).get();
    }

    @Override
    public List<Player> findAll(String name, String title, Race race, Profession profession, Long after, Long before,
                                Boolean banned, Integer minExperience, Integer maxExperience, Integer minLevel,
                                Integer maxLevel, PlayerOrder order, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<Player> players = playerRepository.findAllByNameContainsAndTitleContains(name, title, pageable);

        return players;
    }

    @Override
    public Integer count(String name, String title, Race race, Profession profession, Long after, Long before,
                         Boolean banned, Integer minExperience, Integer maxExperience, Integer minLevel,
                         Integer maxLevel, PlayerOrder order, Integer pageNumber, Integer pageSize) {
        return findAll(name, title, race, profession, after, before, banned, minExperience,
                maxExperience, minLevel, maxLevel, order, pageNumber, pageSize).size();
    }

}
