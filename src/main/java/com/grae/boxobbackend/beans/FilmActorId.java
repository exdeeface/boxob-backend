package com.grae.boxobbackend.beans;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class FilmActorId implements Serializable {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilmActorId that = (FilmActorId) o;
        return Objects.equals(film_id, that.film_id) && Objects.equals(actor_id, that.actor_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(film_id, actor_id);
    }

    private Integer film_id;
    private Integer actor_id;
    public Integer getFilm_id() { return film_id; }
    public Integer getActor_id() { return actor_id; }
}