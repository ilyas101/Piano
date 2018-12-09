package ru.karimov.piano;


import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Created by 777 on 08.12.2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PianoApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testBlankPage() throws Exception {
        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Search")));
    }

    @Test
    public void testSearchQuestions() throws Exception {
        this.mockMvc.perform(post("/")
                .param("query", "java"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Title")))
                .andExpect(content().string(containsString("Next")));

        this.mockMvc.perform(get("/2"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Previous")));
    }

}
