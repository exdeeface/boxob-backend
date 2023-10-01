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

    public void setActor_id(Integer actor_id) { this.actor_id = actor_id; }
    public void setFirst_name(String first_name) { this.first_name = first_name; }
    public void setLast_name(String last_name) { this.last_name = last_name; }
    public void setLast_update() { last_update = Date.valueOf(LocalDate.now()); }

}