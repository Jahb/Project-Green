package nl.tudelft.gogreen.server;

import nl.tudelft.gogreen.server.auth.CreateUser;
import org.hamcrest.Matchers;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HttpTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeClass
    public static void fix() {
        Main.resource = ResourceBundle.getBundle("db", Locale.GERMANY);
    }

    @Test
    public void testHome() throws Exception {
        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Home!")));
    }

    @Test
    public void aaUserLogin() throws Exception {
        Connection conn = DriverManager.getConnection(
                Main.resource.getString("Postgresql.datasource.url"),
                Main.resource.getString("Postgresql.datasource.username"),
                Main.resource.getString("Postgresql.datasource.password"));
        CreateUser.deleteAllUsers(conn);
        this.mockMvc.perform(post("/user/new")
                .param("username", "test").param("password", "kees")).andDo(print()).andExpect(status().isOk());
        this.mockMvc.perform(post("/login").param("username", "test").param("password", "kees")).andDo(print()).andExpect(status().isOk());
        this.mockMvc.perform(post("/user/all")).andDo(print()).andExpect(status().isOk());
        this.mockMvc.perform(get("/logout")).andDo(print()).andExpect(status().isOk());
        this.mockMvc.perform(post("/login")
                .param("username", "test").param("password", "keesasfd")).andDo(print()).andExpect(status().isUnauthorized());
        CreateUser.deleteAllUsers(conn);
        conn.close();
    }

    @AfterClass
    public static void delet() throws Exception {
        Connection conn = DriverManager.getConnection(
                Main.resource.getString("Postgresql.datasource.url"),
                Main.resource.getString("Postgresql.datasource.username"),
                Main.resource.getString("Postgresql.datasource.password"));
        CreateUser.deleteAllUsers(conn);
        conn.close();
    }
}
