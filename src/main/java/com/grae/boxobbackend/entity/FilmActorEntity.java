package com.grae.boxobbackend.entity;

import com.grae.boxobbackend.beans.FilmActorId;
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
    @EmbeddedId
    FilmActorId filmActorId;
    private Date last_update;

    public void setLastUpdate() {
        this.last_update = Date.valueOf(LocalDate.now());
    }

    public FilmActorId getId() {return filmActorId;}
    public Integer getFilm_id() { return filmActorId.getFilm_id(); }
    public Integer getActor_id() { return filmActorId.getActor_id(); }
}
