package com.grae.boxobbackend.controller;

import com.grae.boxobbackend.beans.FilmCategoryId;
import com.grae.boxobbackend.entity.FilmCategoryEntity;
import com.grae.boxobbackend.entity.FilmEntity;
import com.grae.boxobbackend.repo.FilmCategoryRepo;
import com.grae.boxobbackend.repo.FilmRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = {"https://main.dctonckhkul2a.amplifyapp.com", "localhost:3000"})
@RestController
@Controller
public class FilmController {
    @Autowired
    private FilmRepo filmRepo;
    @Autowired
    private FilmCategoryRepo filmCategoryRepo;

    @GetMapping("/films")
    public @ResponseBody Iterable<FilmEntity> getFilms() {
        return filmRepo.findAll();
    }

    @GetMapping("/films/{film_id}")
    public @ResponseBody FilmEntity getFilmById(@PathVariable Integer film_id) {
        return filmRepo.findById(film_id).orElse(null);
    }

    @DeleteMapping("/films/delete/{film_id}")
    public @ResponseBody void deleteById(@PathVariable Integer film_id) {
        filmRepo.deleteRelatedRentals(film_id);
        filmRepo.deleteRelatedInventory(film_id);
        filmRepo.deleteRelatedFilmActors(film_id);
        filmRepo.deleteRelatedFilmCategory(film_id);
        filmRepo.deleteById(film_id);
    }

    @PutMapping("/films/update/{film_id}")
    public @ResponseBody void updateById(@RequestBody FilmEntity film) {
        FilmEntity original = filmRepo.findById(film.getFilmId()).orElse(null);
        if (original != null) {
            original.setTitle(film.getTitle());
            original.setDescription(film.getDescription());
            original.setLength(film.getLength());
            original.setRelease_year(film.getRelease_year());
            original.setRating(film.getRating());
            original.setLanguage_id(film.getLanguageId());
            film.setLast_update();
            filmRepo.save(original);
        }
    }

    @PostMapping("/films/add")
    public @ResponseBody void addFilm(@RequestBody FilmEntity film) {
        film.setLast_update();
        filmRepo.save(film);

        FilmCategoryId filmCategoryId = new FilmCategoryId(film.getFilmId(), film.getCategories().get(0).getCategory_id());

        FilmCategoryEntity filmCategoryEntity = new FilmCategoryEntity();
        filmCategoryEntity.setFilmCategoryId(filmCategoryId);
        filmCategoryEntity.setLastUpdate();
        filmCategoryRepo.save(filmCategoryEntity);
    }
}
