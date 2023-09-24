package com.grae.boxobbackend;

import com.grae.boxobbackend.controller.FilmController;
import com.grae.boxobbackend.entity.FilmEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.runner.RunWith;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
class BoxobBackendApplicationTests {

	@Autowired
	private MockMvc mvc;

	@MockBean
	FilmController filmController;

	@Test
	void testGetFilm() throws Exception {
		FilmEntity film1 = new FilmEntity(1, "ACADEMY DINOSAUR",
				"A Epic Drama of a Feminist And a Mad Scientist who must Battle a Teacher in The Canadian Rockies",
				86, 2006,"PG", 1, 6, 0.99);


		FilmEntity film2 = new FilmEntity(500, "KISS GLORY",
				"A Lacklusture Reflection of a Girl And a Husband who must Find a Robot in The Canadian Rockies",
				163, 2006,"PG-13", 1, 5, 4.99);

		FilmEntity film3 = new FilmEntity(1000, "ZORRO ARK",
				"A Intrepid Panorama of a Mad Scientist And a Boy who must Redeem a Boy in A Monastery",
				50, 2008,"NC-17", 1, 3, 4.99);

		List<FilmEntity> films = Arrays.asList(film1, film2, film3);
		when(filmController.getFilms()).thenReturn(films);

		mvc.perform(get("/films")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[0].film_id").value(films.get(0).getFilmId()));
	}

}
