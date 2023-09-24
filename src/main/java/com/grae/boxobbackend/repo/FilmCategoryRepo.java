package com.grae.boxobbackend.repo;

import com.grae.boxobbackend.entity.FilmCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmCategoryRepo extends JpaRepository<FilmCategoryEntity, Integer> {

}
