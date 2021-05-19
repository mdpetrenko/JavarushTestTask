package com.game.service;

import com.game.entity.Player;
import com.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }


    @Override
    public Player findById(Long id) {
        return playerRepository.findPlayerById(id).get();
    }

    @Override
    public List<Player> findAll() {
        List<Player> players = playerRepository.findAll();
        return players;
    }
}
