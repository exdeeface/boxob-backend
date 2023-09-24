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
@Table(name="film_actor")
@Entity
public class FilmActorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer film_id;
    private Integer actor_id;
    private Date last_update;

    public void setLastUpdate() {
        this.last_update = Date.valueOf(LocalDate.now());
    }

    public Integer getFilm_id() { return film_id; }
    public Integer getActor_id() { return actor_id; }
}
