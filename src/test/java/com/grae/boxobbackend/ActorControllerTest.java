package com.grae.boxobbackend;

import com.grae.boxobbackend.controller.ActorController;
import com.grae.boxobbackend.controller.FilmController;
import com.grae.boxobbackend.entity.ActorEntity;
import com.grae.boxobbackend.repo.ActorRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static com.grae.boxobbackend.FilmControllerTests.asJsonString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

@WebMvcTest(controllers = ActorController.class)
class ActorControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    ActorRepo actorRepo;

    @Test
    void addActor() throws Exception {
        ActorEntity actor = new ActorEntity(13, "first", "last");

        when(actorRepo.save(actor)).thenReturn(actor);

        mvc.perform(post("/actors/add")
                        .content(asJsonString(actor))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }

    @Test
    void getAllActors() throws Exception {
        ActorEntity actor0 = new ActorEntity(15, "CUBA", "OLIVIER");
        ActorEntity actor1 = new ActorEntity(17, "HELEN", "VOIGHT");
        ActorEntity actor2 = new ActorEntity(27, "JULIA", "MCQUEEN");

        List<ActorEntity> actors = Arrays.asList(actor0, actor1, actor2);
        when(actorRepo.findAll()).thenReturn(actors);

        mvc.perform(get("/actors")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
