package com.grae.boxobbackend.controller;

import com.grae.boxobbackend.entity.FilmCategoryEntity;
import com.grae.boxobbackend.repo.FilmCategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@Controller
public class FilmCategoryController {
    @Autowired
    FilmCategoryRepo filmCategoryRepo;

    @PostMapping("/film_category/add")
    public @ResponseBody void addFilmCategory(@RequestBody FilmCategoryEntity filmCategory) {
        filmCategory.setLastUpdate();
        filmCategoryRepo.save(filmCategory);
    }

    @PutMapping("/film_category/update")
    public @ResponseBody void updateFilmCategory(@RequestBody FilmCategoryEntity filmCategory) {
        filmCategory.setLastUpdate();
        if (filmCategoryRepo.findByFilmId(filmCategory.getFilm_id()) != null) {
            FilmCategoryEntity fce = filmCategoryRepo.findByFilmId(filmCategory.getFilm_id());
            filmCategoryRepo.delete(fce);
        }
        filmCategoryRepo.save(filmCategory);
    }
}
