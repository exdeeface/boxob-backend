package com.grae.boxobbackend.repo;

import com.grae.boxobbackend.entity.FilmActorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmActorRepo extends JpaRepository<FilmActorEntity, Integer> {

}
