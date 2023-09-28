package com.grae.boxobbackend.beans;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class FilmCategoryId implements Serializable {
    private Integer film_id;
    private Integer category_id;

    public FilmCategoryId(Integer film_id, Integer category_id) {
        this.film_id = film_id;
        this.category_id = category_id;
    }

    public Integer getFilm_id() { return film_id; }
    public Integer getCategory_id() { return category_id; }
}
