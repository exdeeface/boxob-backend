package com.grae.boxobbackend.entity;

import com.grae.boxobbackend.beans.FilmCategoryId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="film_category")
@Entity
public class FilmCategoryEntity {
    @EmbeddedId
    private FilmCategoryId filmCategoryId;
    private Date last_update;

    public void setLastUpdate() {
        this.last_update = Date.valueOf(LocalDate.now());
    }

    public FilmCategoryId getId() { return filmCategoryId; }
    public Integer getFilm_id() { return filmCategoryId.getFilm_id(); }
    public Integer getCategory_id() { return filmCategoryId.getCategory_id(); }

    public void setFilmCategoryId(FilmCategoryId filmCategoryId) { this.filmCategoryId = filmCategoryId; }
}