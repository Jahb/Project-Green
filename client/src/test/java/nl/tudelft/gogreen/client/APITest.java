package nl.tudelft.gogreen.client;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.mashape.unirest.http.exceptions.UnirestException;
import nl.tudelft.gogreen.client.communication.Api;
import nl.tudelft.gogreen.shared.MessageHolder;
import org.junit.*;

import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.post;

public class APITest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(); // No-args constructor defaults to port 8080


    @Before
    public void before() {
        Api.initApi();
        wireMockRule.stubFor(post("/login").willReturn(ok(Api.gson.toJson(new MessageHolder<>("Login", true)))));
        wireMockRule.stubFor(post("/user/new").willReturn(ok(Api.gson.toJson(new MessageHolder<>("register", true)))));
        wireMockRule.stubFor(post("/feature/total").willReturn(ok(Api.gson.toJson(new MessageHolder<>("Yeet", 10)))));
        wireMockRule.stubFor(post("/feature/new").willReturn(ok(Api.gson.toJson(new MessageHolder<>("Yeet", 10)))));
    }


    @Test
    public void loginTest() throws UnirestException {
        Assert.assertTrue(Api.getTestApi().login("Test", "Kees"));
    }

    @Test
    public void registerTest() throws UnirestException {
        Assert.assertTrue(Api.getTestApi().register("Test", "Kees"));
    }

    @Test
    public void totalTest() {
        Assert.assertEquals(10, Api.getTestApi().getTotal());
    }

    @Test
    public void newFeatureTest() throws UnirestException {
        Assert.assertEquals(10, Api.getTestApi().addFeature("yeet"));
    }

    @Test
    public void apisExist() {
        Assert.assertNotNull(Api.getApi());
        Assert.assertNotNull(Api.getTestApi());
        Assert.assertNotNull(Api.current);
    }

    @After
    public void after() {

    }

}
