package com.grae.boxobbackend.repo;

import com.grae.boxobbackend.entity.FilmEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface FilmRepo extends JpaRepository<FilmEntity, Integer> {
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM rental WHERE inventory_id IN (SELECT inventory_id FROM inventory WHERE film_id=:filmId)", nativeQuery = true)
    void deleteRelatedRentals(@Param("filmId") Integer filmId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM film_actor WHERE film_id=:filmId", nativeQuery = true)
    void deleteRelatedFilmActors(@Param("filmId") Integer filmId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM inventory WHERE film_id=:filmId", nativeQuery = true)
    void deleteRelatedInventory(@Param("filmId") Integer filmId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM film_category WHERE film_id=:filmId", nativeQuery = true)
    void deleteRelatedFilmCategory(@Param("filmId") Integer filmId);
}



