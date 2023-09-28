package com.grae.boxobbackend.beans;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class FilmCategoryId implements Serializable {
    private Integer film_id;
    private Integer category_id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilmCategoryId that = (FilmCategoryId) o;
        return Objects.equals(film_id, that.film_id) && Objects.equals(category_id, that.category_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(film_id, category_id);
    }

    public Integer getFilm_id() { return film_id; }
    public Integer getCategory_id() { return category_id; }

    public void setFilm_id(Integer film_id) { this.film_id = film_id; }
    public void setCategory_id(Integer category_id) { this.category_id = category_id; }
}
