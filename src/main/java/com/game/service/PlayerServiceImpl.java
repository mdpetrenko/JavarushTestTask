package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.repository.PlayerRepository;
import org.hibernate.*;
import org.hibernate.cache.spi.CacheTransactionSynchronization;
import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.engine.jdbc.LobCreator;
import org.hibernate.engine.jdbc.connections.spi.JdbcConnectionAccess;
import org.hibernate.engine.jdbc.spi.JdbcCoordinator;
import org.hibernate.engine.jdbc.spi.JdbcServices;
import org.hibernate.engine.query.spi.sql.NativeSQLQuerySpecification;
import org.hibernate.engine.spi.*;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.internal.SessionImpl;
import org.hibernate.jpa.spi.HibernateEntityManagerImplementor;
import org.hibernate.loader.custom.CustomQuery;
import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.procedure.ProcedureCall;
import org.hibernate.query.Query;
import org.hibernate.query.spi.NativeQueryImplementor;
import org.hibernate.query.spi.QueryImplementor;
import org.hibernate.query.spi.ScrollableResultsImplementor;
import org.hibernate.resource.jdbc.spi.JdbcSessionContext;
import org.hibernate.resource.transaction.spi.TransactionCoordinator;
import org.hibernate.type.descriptor.sql.SqlTypeDescriptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.FlushModeType;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Selection;
import java.io.Serializable;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;
import java.util.stream.Collectors;

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
//        return playerRepository.findAllByNameContainsAndTitleContainsAndBannedAndBirthdayBetweenAndLevelBetweenAndExperienceBetweenAndProfessionAndRace (
//                name, title, banned, after, before, minLevel, maxLevel,
//                minExperience, maxExperience, profession, race, pageable
        return playerRepository.findAll(pageable).stream()
                .filter(player -> player.getName().contains(name))
                .filter(player -> player.getTitle().contains(title))
                .collect(Collectors.toList());
    }

    @Override
    public Integer count(String name, String title, Race race, Profession profession, Long after, Long before,
                         Boolean banned, Integer minExperience, Integer maxExperience, Integer minLevel,
                         Integer maxLevel, PlayerOrder order, Integer pageNumber, Integer pageSize) {
        return findAll(name, title, race, profession, after, before, banned, minExperience,
                maxExperience, minLevel, maxLevel, order, pageNumber, pageSize).size();
    }

}
