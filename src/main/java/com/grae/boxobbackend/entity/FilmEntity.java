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

    public FilmEntity(Integer film_id, String title, String description,
                      Integer length, Integer release_year, String rating,
                      Integer language_id) {
        this.film_id = film_id;
        this.title = title;
        this.description = description;
        this.length = length;
        this.release_year = release_year;
        this.rating = rating;
        this.language_id = language_id;
        this.rental_duration = 0;
        this.rental_rate = 0d;
        setLast_update();
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
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

    public void setLast_update() {
        this.last_update = Date.valueOf(LocalDate.now());
    }

    public List<String> getSpecial_features() {
        if (special_features == null) {
            return new ArrayList<>();
        } else {
            return splitStringToList(special_features);
        }
    }

    public static List<String> splitStringToList(String input) {
        if (input.isEmpty()) {
            return new ArrayList<>();
        } else {
            List<String> resultList = new ArrayList<>();
            String[] elements = input.split(",");
            for (String element : elements) { resultList.add(element.trim()); }
            return resultList;
        }
    }

    public static String correctDescription(String description) {
        String[] words = description.split("\\W+");
        char[] vowels = {'a', 'e', 'i','o', 'u'};

        for (int i = 0; i < words.length; i++) {
            for (char vowel : vowels) {
                if ((words[i].charAt(0) == 'A' || words[i].charAt(0) == 'a') && words[i].length() == 1
                        && (words[i+1].charAt(0) == vowel || words[i+1].charAt(0) == Character.toUpperCase(vowel))) {
                    words[i]+='n';
                }
            }
        }

        StringBuilder newDesc = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            newDesc.append(words[i]);
            if (i < words.length-1) { newDesc.append(" "); }
        }

        return newDesc.toString();
    }

    public Integer getFilmId() { return film_id; }
    public String getTitle() { return title; }
    public Integer getLength() {
        return length;
    }
    public String getRating() { return rating; }
    public String getDescription() { return correctDescription(description); }
    public Integer getLanguageId() { return language_id; }
    public Integer getRelease_year() { return release_year; }
    public List<CategoryEntity> getCategories() { return categories; }
    public List<ActorEntity> getActors() { return actors; }

    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setLength(Integer length) { this.length = length; }
    public void setRelease_year(Integer release_year) { this.release_year = release_year; }
    public void setRating(String rating) { this.rating = rating; }
    public void setLanguage_id(Integer language_id) { this.language_id = language_id; }
    public void setCategories(List<CategoryEntity> categories) { this.categories = categories; }
    public void setActors(List<ActorEntity> actors) { this.actors = actors; }
}