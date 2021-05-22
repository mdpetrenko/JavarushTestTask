package com.game.controller;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/rest/players")
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public List<Player> findPlayers(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "") String title,
            @RequestParam(required = false) Race race,
            @RequestParam(required = false) Profession profession,
            @RequestParam(required = false) Long after,
            @RequestParam(required = false) Long before,
            @RequestParam(required = false) Boolean banned,
            @RequestParam(required = false) Long minExperience,
            @RequestParam(required = false) Long maxExperience,
            @RequestParam(required = false) Long minLevel,
            @RequestParam(required = false) Long maxLevel,
            @RequestParam(required = false) PlayerOrder order,
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "3") Integer pageSize
    ) {

        return playerService.findAll(name, title, race, profession, after, before, banned,
                minExperience, maxExperience, minLevel, maxLevel, order, pageNumber, pageSize);
    }

    @GetMapping("/count")
    public Long count(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "") String title,
            @RequestParam(required = false) Race race,
            @RequestParam(required = false) Profession profession,
            @RequestParam(required = false) Long after,
            @RequestParam(required = false) Long before,
            @RequestParam(required = false) Boolean banned,
            @RequestParam(required = false) Long minExperience,
            @RequestParam(required = false) Long maxExperience,
            @RequestParam(required = false) Long minLevel,
            @RequestParam(required = false) Long maxLevel
    ) {
        return playerService.count(name, title, race, profession, after, before, banned, minExperience, maxExperience,
                minLevel, maxLevel);
    }

    @GetMapping("/{id}")
    public Player findById(@PathVariable Long id) {
        return playerService.findById(id);
    }

//    @PostMapping
//    public Player createPlayer (
//            @RequestBody String name,
//            @RequestBody String title,
//            @RequestBody Race race,
//            @RequestBody Profession profession,
//            @RequestBody Long birthday,
//            @RequestBody(required = false) Boolean banned,
//            @RequestBody Long experience
//    ) {
//        return playerService.saveOrUpdate(name, title, race, profession, birthday, banned, experience);
//    }

    @PostMapping
    public Player createPlayer(@RequestBody Player player) {
        return playerService.createPlayer(player);
    }

    @PostMapping("/{id}")
    public Player onUpdatePlayer(@PathVariable Long id, @RequestBody Player player) {
        return playerService.updatePlayer(id, player);
    }

    @DeleteMapping("/{id}")
    public void removeById(@PathVariable Long id) {
        playerService.delete(id);
    }

}
