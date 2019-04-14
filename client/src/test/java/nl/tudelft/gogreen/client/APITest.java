package nl.tudelft.gogreen.client;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import nl.tudelft.gogreen.client.communication.Api;
import nl.tudelft.gogreen.client.communication.ProfileType;
import nl.tudelft.gogreen.shared.DateHolder;
import nl.tudelft.gogreen.shared.DatePeriod;
import nl.tudelft.gogreen.shared.EventItem;
import nl.tudelft.gogreen.shared.MessageHolder;
import org.junit.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class APITest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(); // No-args constructor defaults to port 8080

    private Map<String, Integer> follow = new HashMap<>();


    @Before
    public void before() {
        Api.initApi();
        follow.put("kees", 1000);
        follow.put("hans", 0);

        wireMockRule.stubFor(post("/login").willReturn(ok(Api.gson.toJson(new MessageHolder<>("Login", true)))));
        wireMockRule.stubFor(post("/user/new").willReturn(ok(Api.gson.toJson(new MessageHolder<>("register", true)))));
        wireMockRule.stubFor(post("/user/all").willReturn(ok(Api.gson.toJson(new MessageHolder<>("empty", Collections.emptyList())))));

        wireMockRule.stubFor(post("/feature/total").willReturn(ok(Api.gson.toJson(new MessageHolder<>("Yeet", 10)))));
        wireMockRule.stubFor(post("/feature/new").willReturn(ok(Api.gson.toJson(new MessageHolder<>("Yeet", 10)))));
        wireMockRule.stubFor(post("/feature/points").willReturn(ok(Api.gson.toJson(new MessageHolder<>("Yeet", Arrays.asList(10, 10, 10, 10))))));
        wireMockRule.stubFor(post("/feature/streak").willReturn(ok(Api.gson.toJson(new MessageHolder<>("Yeet", 10)))));

        wireMockRule.stubFor(post("/stats").willReturn(ok(Api.gson.toJson(new MessageHolder<>("Yeet", new DateHolder(new double[]{1, 2, 3, 4, 5, 6, 7, 8, 9}))))));
        wireMockRule.stubFor(post("/stats/hist").willReturn(ok(Api.gson.toJson(new MessageHolder<>("empty", Collections.emptyList())))));
        wireMockRule.stubFor(post("/stats/quiz").willReturn(ok(Api.gson.toJson(new MessageHolder<>("empty", true)))));

        wireMockRule.stubFor(post("/follow/follow").willReturn(ok(Api.gson.toJson(new MessageHolder<>("follow", true)))));
        wireMockRule.stubFor(post("/follow/unfollow").willReturn(ok(Api.gson.toJson(new MessageHolder<>("follow", true)))));
        wireMockRule.stubFor(post("/follow/followers").willReturn(ok(Api.gson.toJson(new MessageHolder<>("follow", follow)))));
        wireMockRule.stubFor(post("/follow/following").willReturn(ok(Api.gson.toJson(new MessageHolder<>("follow", follow)))));

        wireMockRule.stubFor(post("/event/list").willReturn(ok(Api.gson.toJson(new MessageHolder<>("events", Collections.emptyList())))));
        wireMockRule.stubFor(post("/event/user").willReturn(ok(Api.gson.toJson(new MessageHolder<>("events", Collections.emptyList())))));
        wireMockRule.stubFor(post("/event/new").willReturn(ok(Api.gson.toJson(new MessageHolder<>("events", true)))));
        wireMockRule.stubFor(post("/event/join").willReturn(ok(Api.gson.toJson(new MessageHolder<>("events", true)))));
        wireMockRule.stubFor(post("/event/leave").willReturn(ok(Api.gson.toJson(new MessageHolder<>("events", true)))));

        wireMockRule.stubFor(post("/achievements/names").willReturn(ok(Api.gson.toJson(new MessageHolder<>("empty", Collections.emptyList())))));
        wireMockRule.stubFor(post("/achievements/for").willReturn(ok(Api.gson.toJson(new MessageHolder<>("empty", Collections.emptyList())))));


        wireMockRule.stubFor(post(urlMatching("/*")).willReturn(serverError()));
    }


    @Test
    public void ringSegments() {
        Assert.assertNotNull(Api.getTestApi().getRingSegmentValues("MAIN"));
        Assert.assertNotNull(Api.getTestApi().getRingSegmentValues("NEXT"));
        Assert.assertNotNull(Api.getTestApi().getRingSegmentValues("PREVIOUS"));
    }

    @Test
    public void loginTest() {
        Assert.assertTrue(Api.getTestApi().login("Test", "Kees"));
    }

    @Test
    public void registerTest() {
        Assert.assertTrue(Api.getTestApi().register("Test", "Kees"));
    }

    @Test
    public void totalTest() {
        Assert.assertEquals(10, Api.getTestApi().getTotal());
    }

    @Test
    public void newFeatureTest() {
        Assert.assertEquals(10, Api.getTestApi().addFeature("yeet", 1));
    }

    @Test
    public void pointsTest() {
        Assert.assertEquals(Arrays.asList(10, 10, 10, 10), Api.getTestApi().getFor("Test"));
    }

    @Test
    public void getDatesTest() {
        DateHolder dh = Api.getTestApi().getDatesFor(DatePeriod.WEEK);
        Assert.assertEquals(7, dh.getDays());
        Assert.assertEquals(8, (int) dh.getTotal());
    }

    @Test
    public void followTests() {
        Assert.assertTrue(Api.getTestApi().follow("Kees"));
        Assert.assertTrue(Api.getTestApi().unfollow("Kees"));
        Assert.assertEquals(follow, Api.getTestApi().getFollowers("yeet"));
    }

    @Test
    public void getAllUsersTest() {
        Assert.assertEquals(Collections.emptyList(), Api.getTestApi().getAllUsers());
    }

    @Test
    public void getStreakTest() {
        Assert.assertEquals(10, Api.getTestApi().getStreak());
    }

    @Test
    public void getAllEventsTest() {
        Assert.assertEquals(Collections.emptyList(), Api.getTestApi().getAllEvents());
    }

    @Test
    public void getUserEventsTest() {
        Assert.assertEquals(Collections.emptyList(), Api.getTestApi().getUserEvents());
    }

    @Test
    public void newEventTest() {
        Assert.assertTrue(Api.getTestApi().newEvent(new EventItem("test", "asdf", "yeet", "null?")));
    }

    @Test
    public void joinEventTEst() {
        Assert.assertTrue(Api.getTestApi().joinEvent("test"));
    }

    @Test
    public void leaveEventTest() {
        Assert.assertTrue(Api.getTestApi().leaveEvent("test"));
    }

    @Test
    public void getHistoryForTest() {
        Assert.assertEquals(Collections.emptyList(), Api.getTestApi().getHistoryFor("yeet"));
    }


    @Test
    public void insertQuizDataTest() {
        Assert.assertTrue(Api.getTestApi().insertQuizData(1, 1, true, 1, 1));
    }

    @Test
    public void getAchievementNamesTest() {
        Assert.assertEquals(Collections.emptyList(), Api.getTestApi().getAchievementNames());
    }

    @Test
    public void getAchievementsTest() {
        Assert.assertEquals(Collections.emptyList(), Api.getTestApi().getAchievements("yeet"));
    }

    @Test
    public void apisExist() {
        Assert.assertNotNull(Api.getApi());
        Assert.assertNotNull(Api.getTestApi());
        Assert.assertNotNull(Api.current);
    }

    @Test
    public void profileTest() {
        for (ProfileType ptype : ProfileType.values()) {
            Assert.assertNotNull(ptype);
        }
    }

    @After
    public void after() {

    }

}
