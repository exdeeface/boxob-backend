package com.grae.boxobbackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="film")
@Entity
public class FilmEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer film_id;
    private String title;
    private String description;
    private Integer length;
    private Integer release_year;
    private String rating;
    private Integer language_id;
    private Integer rental_duration;
    private Double rental_rate;
    private Date last_update;

    private String special_features;
    public List<String> getSpecial_features() {
        if (special_features == null) {
            return null;
        } else {
            return splitStringToList(special_features);
        }

    }

    public static List<String> splitStringToList(String input) {
        if (input.isEmpty() || input == null) {
            return new ArrayList<>();
        } else {
            List<String> resultList = new ArrayList<>();
            String[] elements = input.split(",");

            for (String element : elements) { resultList.add(element.trim()); }

            return resultList;
        }
    }

    public void setLastUpdate() {
        this.last_update = Date.valueOf(LocalDate.now());
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("films")
    @JoinTable(name = "film_category",
            joinColumns = {
                    @JoinColumn(name = "film_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "category_id")
            }
    )
    private List<CategoryEntity> categories;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("films")
    @JoinTable(name = "film_actor",
            joinColumns = {
                    @JoinColumn(name = "film_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "actor_id")
            }
    )
    private List<ActorEntity> actors;

    public Integer getFilmId() { return film_id; }
    public String getTitle() { return title; }
    public String getRating() { return rating; }
    public String getDescription() { return description; }
    public Integer getLanguageId() { return language_id; }
}