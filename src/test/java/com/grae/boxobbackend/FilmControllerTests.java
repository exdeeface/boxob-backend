package com.grae.boxobbackend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grae.boxobbackend.controller.FilmController;
import com.grae.boxobbackend.entity.CategoryEntity;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FilmController.class)
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
