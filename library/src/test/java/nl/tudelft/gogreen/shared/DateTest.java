package nl.tudelft.gogreen.shared;

import org.junit.Assert;
import org.junit.Test;

public class DateTest {
    @Test
    public void dateTest() {
        Assert.assertEquals(DatePeriod.WEEK.getIndex(), 7);
        Assert.assertEquals(DatePeriod.MONTH.getNext(), DatePeriod.YEAR);
        Assert.assertEquals(DatePeriod.YEAR.getNext(), DatePeriod.WEEK);
        Assert.assertEquals(DatePeriod.WEEK.getNext(), DatePeriod.MONTH);
    }

    @Test
    public void holderTest() {
        DateHolder holder = new DateHolder(new double[9]);
        Assert.assertEquals(holder.getDays(), 7);
    }
}
