package com.game.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "player")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String name;

    @Column
    private String title;

    @Column
    @Enumerated(EnumType.STRING)
    private Race race;

    @Column
    @Enumerated(EnumType.STRING)
    private Profession profession;

    @Column
    private Long experience;

    @Column
    private Long level;

    @Column
    private Integer untilNextLevel;

    @Column
    @DateTimeFormat
    private Date birthday;

    @Column
    private Boolean banned;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public Long getExperience() {
        return experience;
    }

    public void setExperience(Long experience) {
        this.experience = experience;
    }

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public Integer getUntilNextLevel() {
        return untilNextLevel;
    }

    public void setUntilNextLevel(Integer untilNextLevel) {
        this.untilNextLevel = untilNextLevel;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Boolean getBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    public void computeBanned() {
        if (this.banned == null) banned = false;
    }

    public void computeLevel() {
        setLevel(((long) (Math.sqrt(2500 + 200 * experience)) - 50) / 100);
    }

    public void computeUntilNextLevel() {
        setUntilNextLevel((int) (50 * (level + 1) * (level + 2) - experience));
    }

    public boolean isValid() {
        return name != null && name.trim().length() > 0 && name.length() < 13
                && title != null && title.length() < 31
                && race != null
                && profession != null
                && birthday.getTime() >= 0
                && experience >= 0 && experience <= 10_000_000L;
    }
}
