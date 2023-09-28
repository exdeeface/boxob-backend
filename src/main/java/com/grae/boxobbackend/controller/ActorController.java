package com.grae.boxobbackend.controller;

import com.grae.boxobbackend.entity.ActorEntity;
import com.grae.boxobbackend.repo.ActorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"https://main.dctonckhkul2a.amplifyapp.com", "localhost:3000"})
@RestController
@Controller
public class ActorController {
    @Autowired
    private ActorRepo actorRepo;

    @GetMapping("/actors")
    public @ResponseBody Iterable<ActorEntity> getActors() {
        return actorRepo.findAll();
    }

    @PostMapping("/actors/add")
    public @ResponseBody void addActor(@RequestBody ActorEntity actor) {
        actorRepo.save(actor);
    }
}