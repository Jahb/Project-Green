package nl.tudelft.gogreen.client;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.mashape.unirest.http.exceptions.UnirestException;
import nl.tudelft.gogreen.client.communication.API;
import nl.tudelft.gogreen.shared.MessageHolder;
import org.junit.*;

import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.post;

public class APITest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(); // No-args constructor defaults to port 8080


    @Before
    public void before() {
        API.initAPI();
        wireMockRule.stubFor(post("/login").willReturn(ok(API.gson.toJson(new MessageHolder<>("Login", true)))));
        wireMockRule.stubFor(post("/user/new").willReturn(ok(API.gson.toJson(new MessageHolder<>("register", true)))));
        wireMockRule.stubFor(post("/feature/total").willReturn(ok(API.gson.toJson(new MessageHolder<>("Yeet", 10)))));
        wireMockRule.stubFor(post("/feature/new").willReturn(ok(API.gson.toJson(new MessageHolder<>("Yeet", 10)))));
    }


    @Test
    public void loginTest() throws UnirestException {
        Assert.assertTrue(API.getTestApi().login("Test", "Kees"));
    }

    @Test
    public void registerTest() throws UnirestException {
        Assert.assertTrue(API.getTestApi().register("Test", "Kees"));
    }

    @Test
    public void totalTest() {
        Assert.assertEquals(10, API.getTestApi().getTotal());
    }

    @Test
    public void newFeatureTest() throws UnirestException {
        Assert.assertEquals(10, API.getTestApi().addFeature("yeet"));
    }

    @Test
    public void apisExist(){
        Assert.assertNotNull(API.getApi());
        Assert.assertNotNull(API.getTestApi());
        Assert.assertNotNull(API.current);
    }

    @After
    public void after() {

    }

}
