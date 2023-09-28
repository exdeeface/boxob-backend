package com.grae.boxobbackend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grae.boxobbackend.entity.ActorEntity;
import com.grae.boxobbackend.entity.CategoryEntity;
import com.grae.boxobbackend.entity.FilmEntity;
import com.grae.boxobbackend.repo.ActorRepo;
import com.grae.boxobbackend.repo.FilmRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.runner.RunWith;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
class BoxobBackendApplicationTests {
	@Autowired
	private MockMvc mvc;

	@MockBean
	FilmRepo filmRepo;
	@MockBean
	ActorRepo actorRepo;

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
	void getFilmById() throws Exception {
		FilmEntity film = new FilmEntity(1000, "ZORRO ARK", "A Intrepid Panorama of a Mad Scientist And a Boy who must Redeem a Boy in A Monastery", 50, 2008,"NC-17", 1);

		when(filmRepo.findById(1000)).thenReturn(Optional.of(film));

		mvc.perform(get("/films")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	void addFilm() throws Exception {
		CategoryEntity category = new CategoryEntity(6, "Documentary");
		List<CategoryEntity> categoryList = new ArrayList<>();
		categoryList.add(category);

		FilmEntity film = new FilmEntity(1, "title", "description", 120, 2006,"PG", 1);
		film.setCategories(categoryList);

		when(filmRepo.save(film)).thenReturn(film);

		mvc.perform(MockMvcRequestBuilders.post("/films/add")
						.contentType(MediaType.APPLICATION_JSON)
						.content(asJsonString(film))
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	void addActor() throws Exception {
		ActorEntity actor = new ActorEntity(13, "first", "last");

		when(actorRepo.save(actor)).thenReturn(actor);

		mvc.perform(MockMvcRequestBuilders.post("/actors/add")
						.content(asJsonString(actor))
						.contentType(MediaType.APPLICATION_JSON)
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
	void getAllActors() throws Exception {
		ActorEntity actor0 = new ActorEntity(15, "CUBA", "OLIVIER");
		ActorEntity actor1 = new ActorEntity(17, "HELEN", "VOIGHT");
		ActorEntity actor2 = new ActorEntity(27, "JULIA", "MCQUEEN");

		actor0.setFirst_name("CUBA");
		actor0.setLast_name("OLIVER");
		actor0.setLast_update();

		List<ActorEntity> actors = Arrays.asList(actor0, actor1, actor2);
		when(actorRepo.findAll()).thenReturn(actors);

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
