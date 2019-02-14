package grade;

import org.junit.Assert;
import org.junit.Test;

public class ProjectGradeTest {

    private static final double DELTA = 0.001;

    @Test
    public void getGoodGradeIfCorrect() {

        double grade = ProjectGrade.calculateGrade(true, true, 7.5);

        Assert.assertEquals(7.5, grade, DELTA);
    }

    @Test
    public void getBadGradeIfNotCompiles() {

        double grade = ProjectGrade.calculateGrade(true, false, 7.5);

        Assert.assertEquals(1, grade, DELTA);
    }

    @Test
    public void getBadGradeIfNotUsedGit() {

        double grade = ProjectGrade.calculateGrade(false, true, 7.5);

        Assert.assertEquals(1, grade, DELTA);
    }

    @Test
    public void testTaIsNotHappy() {
        Assert.assertFalse(ProjectGrade.taIsHappy(false));
    }

    @Test
    public void testTaIsHappy() {
        Assert.assertTrue(ProjectGrade.taIsHappy(true));
    }

    @Test
    public void testIsUsingLambda() {
        Assert.assertTrue(ProjectGrade.usesLambda());
    }
}