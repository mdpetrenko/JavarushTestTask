package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.repository.PlayerRepository;
import com.game.repository.PlayerSpecification;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Player findById(Long id) {
        if (id < 1) throw new NumberFormatException("Not a valid id. Must be Integer > 0");
        return playerRepository.findById(id).orElseThrow(NumberFormatException::new);
    }

    @Override
    public List<Player> findAll(String name, String title, Race race, Profession profession, Long after, Long before,
                                Boolean banned, Long minExp, Long maxExp, Long minLevel,
                                Long maxLevel, PlayerOrder order, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Specification<Player> playerSpecification =
                Objects.requireNonNull(PlayerSpecification.nameAndTitleSpec(name, title)
                        .and(PlayerSpecification.betweenSpec("experience", minExp, maxExp))
                        .and(PlayerSpecification.betweenSpec("level", minLevel, maxLevel)))
                        .and(PlayerSpecification.dateBetween(after, before))
                        .and(PlayerSpecification.equalSpec("banned", banned))
                        .and(PlayerSpecification.equalSpec("race", race))
                        .and(PlayerSpecification.equalSpec("profession", profession));
        return playerRepository.findAll(playerSpecification, pageable).getContent();
    }

    @Override
    public Long count(String name, String title, Race race, Profession profession, Long after, Long before,
                      Boolean banned, Long minExp, Long maxExp, Long minLevel,
                      Long maxLevel) {
        Specification<Player> playerSpecification =
                Objects.requireNonNull(PlayerSpecification.nameAndTitleSpec(name, title)
                        .and(PlayerSpecification.betweenSpec("experience", minExp, maxExp)))
                        .and(PlayerSpecification.betweenSpec("level", minLevel, maxLevel))
                        .and(PlayerSpecification.betweenSpec("birthday", after, before))
                        .and(PlayerSpecification.equalSpec("banned", banned))
                        .and(PlayerSpecification.equalSpec("race", race))
                        .and(PlayerSpecification.equalSpec("profession", profession));
        return playerRepository.count(playerSpecification);
    }

}
