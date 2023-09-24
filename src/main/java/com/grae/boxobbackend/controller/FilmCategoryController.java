package com.grae.boxobbackend.controller;

import com.grae.boxobbackend.entity.FilmCategoryEntity;
import com.grae.boxobbackend.repo.FilmCategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "https://localhost:3000")
@RestController
@Controller
public class FilmCategoryController {
    @Autowired
    FilmCategoryRepo filmCategoryRepo;

    @PutMapping("/film_category/add/")
    public @ResponseBody void addFilmCategory(@RequestBody FilmCategoryEntity filmCategory) {
        filmCategory.setLastUpdate();
        System.out.println("film_id: " +  filmCategory.getFilm_id() + " category_id: " + filmCategory.getCategory_id());
        //filmCategoryRepo.save(filmCategory);
    }
}
