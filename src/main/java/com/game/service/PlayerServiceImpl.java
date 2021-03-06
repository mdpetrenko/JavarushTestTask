package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.repository.PlayerRepository;
import com.game.repository.PlayerSpecification;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public List<Player> findAll(String name, String title, Race race, Profession profession, Long after, Long before,
                                Boolean banned, Long minExp, Long maxExp, Long minLevel,
                                Long maxLevel, PlayerOrder order, Integer pageNumber, Integer pageSize) {
        String orderBy = order == null ? PlayerOrder.ID.getFieldName() : order.getFieldName();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, orderBy));

        Specification<Player> playerSpecification = getPlayerSpecification(name, title, race, profession, after, before, banned, minExp, maxExp, minLevel, maxLevel);
        return playerRepository.findAll(playerSpecification, pageable).getContent();
    }

    @Override
    public Long count(String name, String title, Race race, Profession profession, Long after, Long before,
                      Boolean banned, Long minExp, Long maxExp, Long minLevel,
                      Long maxLevel) {
        Specification<Player> playerSpecification = getPlayerSpecification(name, title, race, profession,
                after, before, banned, minExp, maxExp, minLevel, maxLevel);
        return playerRepository.count(playerSpecification);
    }

    @Override
    public Player findById(Long id) {
        if (id < 1) throw new NumberFormatException("Not a valid id. Must be Integer > 0");
        return playerRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Player createPlayer(Player player) {
        if (!player.isValid()) {
            throw new IllegalArgumentException();
        }
        player.computeBanned();
        player.computeLevel();
        player.computeUntilNextLevel();
        playerRepository.save(player);
        return player;
    }

    @Override
    public Player updatePlayer(Long id, Player updatedPlayer) {
        Player player = findById(id);
        if (updatedPlayer.getName() != null) {
            player.setName(updatedPlayer.getName());
        }
        if (updatedPlayer.getTitle() != null) {
            player.setTitle(updatedPlayer.getTitle());
        }
        if (updatedPlayer.getRace() != null) {
            player.setRace(updatedPlayer.getRace());
        }
        if (updatedPlayer.getProfession() != null) {
            player.setProfession(updatedPlayer.getProfession());
        }
        if (updatedPlayer.getBirthday() != null) {
            player.setBirthday(updatedPlayer.getBirthday());
        }
        if (updatedPlayer.getBanned() != null) {
            player.setBanned(updatedPlayer.getBanned());
        }
        if (updatedPlayer.getExperience() != null) {
            player.setExperience(updatedPlayer.getExperience());
            player.computeLevel();
            player.computeUntilNextLevel();
        }
        return createPlayer(player);
    }

    @Override
    public void delete(Long id) {
        if (id < 1) {
            throw new NumberFormatException();
        }
        playerRepository.deleteById(id);
    }

    private Specification<Player> getPlayerSpecification(String name, String title, Race race, Profession profession,
                                                         Long after, Long before, Boolean banned,
                                                         Long minExp, Long maxExp, Long minLevel, Long maxLevel) {
        return PlayerSpecification.nameAndTitleSpec(name, title)
                .and(PlayerSpecification.betweenSpec("experience", minExp, maxExp))
                .and(PlayerSpecification.betweenSpec("level", minLevel, maxLevel))
                .and(PlayerSpecification.dateBetween(after, before))
                .and(PlayerSpecification.equalSpec("banned", banned))
                .and(PlayerSpecification.equalSpec("race", race))
                .and(PlayerSpecification.equalSpec("profession", profession));
    }

}
