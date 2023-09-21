package com.grae.boxobbackend.repo;

import com.grae.boxobbackend.entity.FilmEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmRepo extends JpaRepository<FilmEntity, Integer> {

}

