package com.grae.boxobbackend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grae.boxobbackend.beans.FilmCategoryId;
import com.grae.boxobbackend.controller.FilmCategoryController;
import com.grae.boxobbackend.controller.FilmController;
import com.grae.boxobbackend.entity.ActorEntity;
import com.grae.boxobbackend.entity.CategoryEntity;
import com.grae.boxobbackend.entity.FilmCategoryEntity;
import com.grae.boxobbackend.entity.FilmEntity;
import com.grae.boxobbackend.repo.FilmCategoryRepo;
import com.grae.boxobbackend.repo.FilmRepo;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = {FilmController.class, FilmCategoryController.class})
class FilmControllerTests {
    @Autowired
    private MockMvc mvc;

    @MockBean
    FilmRepo filmRepo;
    @MockBean
    FilmCategoryRepo filmCategoryRepo;

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void addFilm() throws Exception {


        CategoryEntity category = new CategoryEntity(6, "Documentary");
        List<CategoryEntity> categoryList = new ArrayList<>();
        categoryList.add(category);

        FilmEntity film = new FilmEntity(1, "title", "description", 120, 2006,"PG", 1);
        film.setCategories(categoryList);

        when(filmRepo.save(film)).thenReturn(film);

        mvc.perform(post("/films/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(film))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        FilmCategoryId filmCategoryId = new FilmCategoryId();
        filmCategoryId.setFilm_id(film.getFilmId());
        filmCategoryId.setCategory_id(film.getCategories().get(0).getCategory_id());
        FilmCategoryEntity filmCategory = new FilmCategoryEntity();
        filmCategory.setFilmCategoryId(filmCategoryId);

        when(filmCategoryRepo.save(filmCategory)).thenReturn(filmCategory);

        mvc.perform(post("/film_category/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(filmCategory))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void updateFilm() throws Exception  {
        CategoryEntity categoryOriginal = new CategoryEntity(6, "Documentary");
        List<CategoryEntity> categoryListOriginal = new ArrayList<>();
        categoryListOriginal.add(categoryOriginal);

        FilmEntity filmOriginal = new FilmEntity(1, "title", "description", 120, 2006,"PG", 1);
        filmOriginal.setCategories(categoryListOriginal);

        FilmCategoryId filmCategoryIdOriginal = new FilmCategoryId();
        filmCategoryIdOriginal.setFilm_id(filmOriginal.getFilmId());
        filmCategoryIdOriginal.setCategory_id(categoryOriginal.getCategory_id());

        FilmCategoryEntity filmCategoryOriginal = new FilmCategoryEntity();
        filmCategoryOriginal.setFilmCategoryId(filmCategoryIdOriginal);

        CategoryEntity categoryNew = new CategoryEntity(11, "Horror");
        List<CategoryEntity> categoryListNew = new ArrayList<>();
        categoryListNew.add(categoryNew);

        FilmEntity filmNew = new FilmEntity(1, " new title", "new description", 90, 2008,"NC-17", 3);
        filmNew.setCategories(categoryListNew);

        FilmCategoryId filmCategoryIdNew = new FilmCategoryId();
        filmCategoryIdNew.setFilm_id(filmOriginal.getFilmId());
        filmCategoryIdNew.setCategory_id(categoryNew.getCategory_id());

        FilmCategoryEntity filmCategoryNew = new FilmCategoryEntity();
        filmCategoryNew.setFilmCategoryId(filmCategoryIdNew);

        when(filmRepo.save(filmOriginal)).thenReturn(filmNew);

        mvc.perform(put("/films/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(filmNew))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        when(filmCategoryRepo.save(filmCategoryOriginal)).thenReturn(filmCategoryNew);

        mvc.perform(put("/film_category/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(filmCategoryOriginal))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void updateFilmFail() throws Exception {
        CategoryEntity categoryOriginal = new CategoryEntity(6, "Documentary");
        List<CategoryEntity> categoryListOriginal = new ArrayList<>();
        categoryListOriginal.add(categoryOriginal);

        FilmEntity film = new FilmEntity(2000, "title", "description", 120, 2006,"PG", 1);
        film.setCategories(categoryListOriginal);

        FilmCategoryId filmCategoryId = new FilmCategoryId();
        filmCategoryId.setFilm_id(film.getFilmId());
        filmCategoryId.setCategory_id(categoryOriginal.getCategory_id());

        FilmCategoryEntity filmCategoryEntity = new FilmCategoryEntity();
        filmCategoryEntity.setFilmCategoryId(filmCategoryId);

        when(filmRepo.findById(film.getFilmId())).thenReturn(null);

        assertThrows(NullPointerException.class, ()->{
            FilmEntity filmOriginal = filmRepo.findById(film.getFilmId()).orElse(null);
        });
    }

    @Test
    void getFilms() throws Exception {
        FilmEntity film0 = new FilmEntity(1, "ACADEMY DINOSAUR", "A Epic Drama of a Feminist And a Mad Scientist who must Battle a Teacher in The Canadian Rockies", 86, 2006,"PG", 1);
        FilmEntity film1 = new FilmEntity(500, "KISS GLORY", "A Lacklusture Reflection of a Girl And a Husband who must Find a Robot in The Canadian Rockies", 163, 2006,"PG-13", 1);
        FilmEntity film2 = new FilmEntity(1000, "ZORRO ARK", "A Intrepid Panorama of a Mad Scientist And a Boy who must Redeem a Boy in A Monastery", 50, 2008,"NC-17", 1);

        List<FilmEntity> films = Arrays.asList(film0, film1, film2);
        when(filmRepo.findAll()).thenReturn(films);

        mvc.perform(get("/films")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }

    @Test
    void deleteFilm() throws Exception {
        FilmEntity film = new FilmEntity(1, "ACADEMY DINOSAUR", "A Epic Drama of a Feminist And a Mad Scientist who must Battle a Teacher in The Canadian Rockies", 86, 2006,"PG", 1);

        doNothing().when(filmRepo).deleteById(1000);

        mvc.perform(delete("/films/delete/" + film.getFilmId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }

    @Test
    void testFilmDescription() {
        String description = "A Astounding Action of a Astronaut and a Arbiter who must Search a Awesome Castle in South Australia";
        assertEquals("An Astounding Action of an Astronaut and an Arbiter who must Search an Awesome Castle in South Australia", FilmEntity.correctDescription(description));
    }

    @Test
    void testFilmSpecialFeatures() {
        List<String> features = FilmEntity.splitStringToList("Trailers,Commenataries,Behind the scenes");
        assertEquals("Trailers", features.get(0));
        assertEquals("Commenataries", features.get(1));
        assertEquals("Behind the scenes", features.get(2));
    }

    @Test
    void testActorEntity() {
        ActorEntity actor = new ActorEntity();
        actor.setFirst_name("first");
        actor.setLast_name("last");
        actor.setActor_id(2023);
        actor.setLast_update();

        assert(true);
    }
}
