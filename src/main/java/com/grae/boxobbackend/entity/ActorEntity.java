package com.grae.boxobbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "actor")
public class ActorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer actor_id;
    private String first_name;
    private String last_name;
    private Date last_update;

    @ManyToMany(mappedBy = "actors", fetch = FetchType.LAZY)
    private List<FilmEntity> films;

    public ActorEntity(Integer actor_id, String first_name, String last_name) {
        this.actor_id = actor_id;
        this.first_name = first_name;
        this.last_name = last_name;
        setLast_update();
    }

    public Integer getActor_id() { return actor_id; }
    public String getFirst_name() { return first_name;}
    public String getLast_name() { return last_name; }
    public Date getLast_update() { return last_update; }
    public List<FilmEntity> getFilms() { return films; }

    public void setActor_id(Integer actor_id) { this.actor_id = actor_id; }
    public void setFirst_name(String first_name) { this.first_name = first_name;}
    public void setLast_name(String last_name) { this.last_name = last_name; }
    public void setLast_update() { this.last_update = Date.valueOf(LocalDate.now()); }
    public void setFilms(List<FilmEntity> films) { this.films = films; }
}