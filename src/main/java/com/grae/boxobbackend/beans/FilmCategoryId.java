package com.grae.boxobbackend.beans;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class FilmCategoryId implements Serializable {
    private Integer film_id;

    private Integer category_id;

    public Integer getFilm_id() { return film_id; }
    public Integer getCategory_id() { return category_id; }
    public void setCategory_id(Integer category_id) { this.category_id = category_id; }
    public void setFilm_id(Integer film_id) { this.film_id = film_id; }
}
