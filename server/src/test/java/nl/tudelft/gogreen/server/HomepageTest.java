package nl.tudelft.gogreen.server;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HomepageTest {

    @Spy
    private Homepage cs = new Homepage();

    @Before
    public void before() {

        Mockito.doReturn("yeet").when(cs).getHomeString();
    }

    @Test
    public void aaHomeTest(){
        Assert.assertEquals("yeet", cs.home());
    }

}
