package com.grae.boxobbackend.controller;

import com.grae.boxobbackend.entity.CategoryEntity;
import com.grae.boxobbackend.repo.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Controller
public class CategoryController {
    @Autowired
    private CategoryRepo categoryRepo;

    @GetMapping("/categories")
    public @ResponseBody Iterable<CategoryEntity> getCategories() {
        return categoryRepo.findAll();
    }
}
