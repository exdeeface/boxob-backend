package com.grae.boxobbackend.repo;

import com.grae.boxobbackend.entity.FilmEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface FilmRepo extends JpaRepository<FilmEntity, Integer> {
    FilmEntity findByTitleAndDescription(String title, String description);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM rental WHERE inventory_id IN (SELECT inventory_id FROM inventory WHERE film_id=:film_id)", nativeQuery = true)
    void deleteRelatedRentals(@Param("film_id") Integer film_id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM film_actor WHERE film_id=:film_id", nativeQuery = true)
    void deleteRelatedFilmActors(@Param("film_id") Integer film_id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM inventory WHERE film_id=:film_id", nativeQuery = true)
    void deleteRelatedInventory(@Param("film_id") Integer film_id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM film_category WHERE film_id=:film_id", nativeQuery = true)
    void deleteRelatedFilmCategory(@Param("film_id") Integer film_id);
}



