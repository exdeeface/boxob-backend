package com.grae.boxobbackend.entity;

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
    @Id
    private Integer film_id;
    private Integer category_id;
    private Date last_update;

    public void setLastUpdate() {
        this.last_update = Date.valueOf(LocalDate.now());
    }

    public Integer getFilm_id() { return film_id; }
    public Integer getCategory_id() { return category_id; }
}
