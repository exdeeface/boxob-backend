package com.grae.boxobbackend.controller;

import com.grae.boxobbackend.entity.FilmActorEntity;
import com.grae.boxobbackend.repo.FilmActorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@Controller
public class FilmActorController {
    @Autowired
    FilmActorRepo filmActorRepo;

    @PostMapping("/film_actor/add")
    public @ResponseBody void addFilmActor(@RequestBody FilmActorEntity filmActor) {
        filmActor.setLastUpdate();
        filmActorRepo.save(filmActor);
    }
}
