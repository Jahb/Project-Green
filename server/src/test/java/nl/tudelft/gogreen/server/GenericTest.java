package nl.tudelft.gogreen.server;

import nl.tudelft.gogreen.server.auth.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class GenericTest {
    @Test
    public void userTest() {
        User u = new User("Test", "Kees");
        assertThat(u.getUsername()).isEqualTo("Test");
        assertThat(u.getPassword()).isEqualTo("Kees");
        assertThat(u).isEqualTo(new User("Test", "Kees"));
        assertThat(u.hashCode()).isEqualTo(new User("Test", "Kees").hashCode());
    }


}
