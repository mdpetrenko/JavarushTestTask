package com.game.repository;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import org.springframework.cglib.core.Predicate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    List<Player> findAll(Predicate predicate, Pageable pageable);

    List<Player> findAllByNameContainsAndTitleContainsAndBannedAndBirthdayBetweenAndLevelBetweenAndExperienceBetweenAndProfessionAndRace (
            String name, String title, Boolean banned, Long minBirthday, Long maxBirthday,
            Integer minLevel, Integer maxLevel, Integer minExperience, Integer maxExperience,
            Profession profession, Race race, Pageable pageable
    );

}
