package com.game.repository;

import com.game.entity.Player;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class PlayerSpecification {
    public static Specification<Player> nameAndTitleSpec(final String name, final String title) {
        return (r, cq, cb) -> cb.and(cb.like(r.get("name"), "%" + name + "%"),
                cb.like(r.get("title"), "%" + title + "%"));
    }

    public static Specification<Player> dateBetween(final Long after, final Long before) {
        if (after != null && before != null) {
            return (r, cq, cb) -> cb.between( r.get("birthday"), new Date(after), new Date(before));
        }
        if (after != null) {
            return (r, cq, cb) -> cb.greaterThanOrEqualTo(r.get("birthday"), new Date(after));
        }
        if (before != null) {
            return (r, cq, cb) -> cb.lessThanOrEqualTo(r.get("birthday"), new Date(before));
        }
        return null;
    }

    public static Specification<Player> betweenSpec(final String title, final Long min, final Long max) {
        if (min != null && max != null) {
            return (r, cq, cb) -> cb.between(r.get(title), min, max);
        }
        if (min != null) {
            return (r, cq, cb) -> cb.greaterThanOrEqualTo(r.get(title), min);
        }
        if (max != null) {
            return (r, cq, cb) -> cb.lessThanOrEqualTo(r.get(title), max);
        }
        return null;
    }

    public static Specification<Player> equalSpec(final String title, final Object obj) {
        if (obj != null) {
            return (r, cq, cb) -> cb.equal(r.get(title), obj);
        }
        return null;
    }

}
