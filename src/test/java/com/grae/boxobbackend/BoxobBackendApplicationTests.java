package com.grae.boxobbackend;

import com.grae.boxobbackend.controller.ActorController;
import com.grae.boxobbackend.controller.FilmController;
import com.grae.boxobbackend.entity.ActorEntity;
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

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

	@MockBean
	ActorController actorController;

	@Test
	void testGetFilms() throws Exception {
		FilmEntity film0 = new FilmEntity(1, "ACADEMY DINOSAUR", "A Epic Drama of a Feminist And a Mad Scientist who must Battle a Teacher in The Canadian Rockies", 86, 2006,"PG", 1);
		FilmEntity film1 = new FilmEntity(500, "KISS GLORY", "A Lacklusture Reflection of a Girl And a Husband who must Find a Robot in The Canadian Rockies", 163, 2006,"PG-13", 1);
		FilmEntity film2 = new FilmEntity(1000, "ZORRO ARK", "A Intrepid Panorama of a Mad Scientist And a Boy who must Redeem a Boy in A Monastery", 50, 2008,"NC-17", 1);

		List<FilmEntity> films = Arrays.asList(film0, film1, film2);
		when(filmController.getFilms()).thenReturn(films);

		mvc.perform(get("/films")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[0].film_id").value(film0.getFilmId()))
			.andExpect(jsonPath("$[1].film_id").value(film1.getFilmId()))
			.andExpect(jsonPath("$[2].film_id").value(film2.getFilmId()))
			.andExpect(jsonPath("$", hasSize(3)));
	}

	@Test
	void testDeleteFilm() throws Exception {
		FilmEntity film0 = new FilmEntity(1, "ACADEMY DINOSAUR", "A Epic Drama of a Feminist And a Mad Scientist who must Battle a Teacher in The Canadian Rockies", 86, 2006,"PG", 1);
		FilmEntity film1 = new FilmEntity(500, "KISS GLORY", "A Lacklusture Reflection of a Girl And a Husband who must Find a Robot in The Canadian Rockies", 163, 2006,"PG-13", 1);
		FilmEntity film2 = new FilmEntity(1000, "ZORRO ARK", "A Intrepid Panorama of a Mad Scientist And a Boy who must Redeem a Boy in A Monastery", 50, 2008,"NC-17", 1);

		List<FilmEntity> films = Arrays.asList(film0, film1, film2);
		when(filmController.getFilms()).thenReturn(films);

		mvc.perform(delete("/films/delete/" + film2.getFilmId())
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(200));

		when(filmController.getFilms()).thenReturn(films);
	}

	@Test
	void getAllActors() throws Exception {
		ActorEntity actor0 = new ActorEntity(15, "CUBA", "OLIVIER");
		ActorEntity actor1 = new ActorEntity(17, "HELEN", "VOIGHT");
		ActorEntity actor2 = new ActorEntity(27, "JULIA", "MCQUEEN");

		actor0.setFirst_name("CUBA");
		actor0.setLast_name("OLIVER");
		actor0.setLast_update();

		List<ActorEntity> actors = Arrays.asList(actor0, actor1, actor2);
		when(actorController.getActors()).thenReturn(actors);

		mvc.perform(get("/actors")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].actor_id").value(actor0.getActor_id()))
				.andExpect(jsonPath("$[1].actor_id").value(actor1.getActor_id()))
				.andExpect(jsonPath("$[2].actor_id").value(actor2.getActor_id()))
				.andExpect(jsonPath("$", hasSize(3)));
	}

	@Test
	void testFilmSpecialFeatures() {
		List<String> features = FilmEntity.splitStringToList("Trailers,Commenataries,Behind the scenes");
		assertEquals("Trailers", features.get(0));
		assertEquals("Commenataries", features.get(1));
		assertEquals("Behind the scenes", features.get(2));
	}

	@Test
	void testFilmDescription() {
		String description = "A Astounding Action of a Astronaut and a Arbiter who must Search a Awesome Castle in South Australia";
		assertEquals("An Astounding Action of an Astronaut and an Arbiter who must Search an Awesome Castle in South Australia", FilmEntity.correctDescription(description));
	}
}
