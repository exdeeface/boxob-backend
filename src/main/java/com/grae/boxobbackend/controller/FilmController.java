package com.grae.boxobbackend.controller;

import com.grae.boxobbackend.entity.ActorEntity;
import com.grae.boxobbackend.entity.FilmEntity;
import com.grae.boxobbackend.repo.FilmRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "https://localhost:3000")
@RestController
@Controller
public class FilmController {
    @Autowired
    private FilmRepo filmRepo;

    @GetMapping("/films")
    public @ResponseBody Iterable<FilmEntity> getFilms() {
        return filmRepo.findAll();
    }

    @GetMapping("/films/{film_id}")
    public @ResponseBody FilmEntity getFilmById(@PathVariable Integer film_id) {
        return filmRepo.findById(film_id).orElse(null);
    }

    @PostMapping("/films/add")
    public @ResponseBody void addFilm(FilmEntity film) {
        filmRepo.save(film);
    }
}
