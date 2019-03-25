package nl.tudelft.gogreen.server;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

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

    private static ResourceBundle resource = ResourceBundle.getBundle("db");


    @Test
    public void testHome() throws Exception {
        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Home!")));
    }

    @Test
    public void testUserLogin() throws Exception {
        Connection conn = DriverManager.getConnection(
                resource.getString("Postgresql.datasource.url"),
                resource.getString("Postgresql.datasource.username"),
                resource.getString("Postgresql.datasource.password"));
        int id = NewFeature.getId("test", conn);
        if (id != -1)
            CreateUser.delete_user(id, conn);
        this.mockMvc.perform(post("/user/new")
                .param("username", "test").param("password", "kees")).andDo(print()).andExpect(status().isOk());
        this.mockMvc.perform(post("/login").param("username", "test").param("password", "kees")).andDo(print()).andExpect(status().isOk());
        this.mockMvc.perform(post("/feature/new").param("feature", "Vegetarian Meal")).andDo(print()).andExpect(status().isUnauthorized());
        this.mockMvc.perform(post("/feature/total")).andDo(print()).andExpect(status().isOk());
    }


}
