package nl.tudelft.gogreen.server;

import nl.tudelft.gogreen.server.auth.User;
import nl.tudelft.gogreen.server.auth.VerifyUser;
import nl.tudelft.gogreen.shared.auth.AuthAgreement;
import nl.tudelft.gogreen.shared.auth.UserAuth;
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

    @Test
    public void verifyTest() {
        User u = new User("Test", "testpw");
        assertThat(VerifyUser.findUser("Test")).isEqualTo(u);
        assertThat(VerifyUser.checkPassword(u, "testpw")).isTrue();
        assertThat(VerifyUser.checkPassword(u, "Kees")).isFalse();
        assertThat(VerifyUser.storeKey("Test")).hasSize(128);
    }

    @Test
    public void authTest() {
        UserAuth good = new UserAuth("Test", "testpw");
        UserAuth bad = new UserAuth("Test", "Kees");
        AuthAgreement goodAuth = VerifyUser.authUser(good);
        AuthAgreement badAuth = VerifyUser.authUser(bad);

        assertThat(goodAuth.isSuccess()).isTrue();
        assertThat(badAuth.isSuccess()).isFalse();

        assertThat(goodAuth.getUsername()).isEqualTo("Test");
        assertThat(badAuth.getUsername()).isEqualTo("Test");

        assertThat(badAuth.getKey()).isNull();
    }
}
