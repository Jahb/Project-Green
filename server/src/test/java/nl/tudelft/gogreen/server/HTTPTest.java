package nl.tudelft.gogreen.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.tudelft.gogreen.shared.auth.AuthAgreement;
import nl.tudelft.gogreen.shared.auth.UserAuth;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HTTPTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper jacksonObjectMapper;

    @Test
    public void testHome() throws Exception {
        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Home!")));
    }

    @Test
    public void testUserLogin() throws Exception {
        this.mockMvc.perform(post("/auth").contentType(MediaType.APPLICATION_JSON)
                .content(jacksonObjectMapper.writeValueAsString(new UserAuth("Test", "testpw")))).andDo(print()).andExpect(status().isOk())
                .andExpect(result -> {
                    AuthAgreement aa = jacksonObjectMapper.readValue(result.getResponse().getContentAsString(), AuthAgreement.class);
                    assertThat(aa.getUsername()).isEqualTo("Test");
                    assertThat(aa.isSuccess()).isTrue();
                });
    }
}
