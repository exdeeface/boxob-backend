package com.grae.boxobbackend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grae.boxobbackend.beans.FilmCategoryId;
import com.grae.boxobbackend.controller.FilmCategoryController;
import com.grae.boxobbackend.controller.FilmController;
import com.grae.boxobbackend.entity.CategoryEntity;
import com.grae.boxobbackend.entity.FilmCategoryEntity;
import com.grae.boxobbackend.entity.FilmEntity;
import com.grae.boxobbackend.repo.FilmCategoryRepo;
import com.grae.boxobbackend.repo.FilmRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
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
    }

    @Test
    void getFilms() throws Exception {
        FilmEntity film0 = new FilmEntity(1, "ACADEMY DINOSAUR", "A Epic Drama of a Feminist And a Mad Scientist who must Battle a Teacher in The Canadian Rockies", 86, 2006,"PG", 1);
        FilmEntity film1 = new FilmEntity(500, "KISS GLORY", "A Lacklusture Reflection of a Girl And a Husband who must Find a Robot in The Canadian Rockies", 163, 2006,"PG-13", 1);
        FilmEntity film2 = new FilmEntity(1000, "ZORRO ARK", "A Intrepid Panorama of a Mad Scientist And a Boy who must Redeem a Boy in A Monastery", 50, 2008,"NC-17", 1);

        FilmCategoryId filmCategoryId0 = new FilmCategoryId(1, 6);
        FilmCategoryId filmCategoryId1 = new FilmCategoryId(500, 10);
        FilmCategoryId filmCategoryId2 = new FilmCategoryId(1000, 3);

        FilmCategoryEntity filmCategory0 = new FilmCategoryEntity();
        FilmCategoryEntity filmCategory1 = new FilmCategoryEntity();
        FilmCategoryEntity filmCategory2 = new FilmCategoryEntity();

        filmCategory0.setFilmCategoryId(filmCategoryId0);
        filmCategory1.setFilmCategoryId(filmCategoryId1);
        filmCategory2.setFilmCategoryId(filmCategoryId2);

        List<FilmEntity> films = Arrays.asList(film0, film1, film2);
        List<FilmCategoryEntity> filmCategories = Arrays.asList(filmCategory0, filmCategory1, filmCategory2);
        when(filmRepo.findAll()).thenReturn(films);
        when(filmCategoryRepo.findAll()).thenReturn(filmCategories);

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
}
