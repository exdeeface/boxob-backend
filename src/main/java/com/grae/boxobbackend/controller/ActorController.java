package com.grae.boxobbackend.controller;

import com.grae.boxobbackend.entity.ActorEntity;
import com.grae.boxobbackend.repo.ActorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Controller
public class ActorController {
    @Autowired
    private ActorRepo actorRepo;

    @GetMapping("/actors")
    public @ResponseBody Iterable<ActorEntity> getActors() {
        return actorRepo.findAll();
    }
}