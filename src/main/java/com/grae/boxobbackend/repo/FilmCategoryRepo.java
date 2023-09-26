package com.grae.boxobbackend.repo;

import com.grae.boxobbackend.entity.FilmCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface FilmCategoryRepo extends JpaRepository<FilmCategoryEntity, Integer> {

    @Transactional
    @Query(value = "SELECT * FROM film_category WHERE film_id=:film_id", nativeQuery = true)
    FilmCategoryEntity findByFilmId(@Param("film_id") Integer film_id);
}
