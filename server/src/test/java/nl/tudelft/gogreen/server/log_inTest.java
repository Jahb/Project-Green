//package nl.tudelft.gogreen.server;
//
//import org.testng.annotations.Test;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.util.ResourceBundle;
//
//public class log_inTest {
//
//    private static ResourceBundle resource = ResourceBundle.getBundle("db");
//    @org.junit.Before
//    public void setUp() throws Exception {
//        System.out.println("Before");
//        Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"));
//        create_user.create_user("Leo", "Messi",conn);
//    }
//
//    @org.junit.After
//    public void tearDown() throws Exception {
//        System.out.println("After");
//        //Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"));
//        //PreparedStatement user = conn.prepareStatement("delete from user_table where username = Leo");
//    }
//
//    @Test
//    public void log_in() {
//        try {
//            System.out.println("now");
//            //Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"));
//            //assertTrue(log_in.log_in("Leo", "Messi", conn));
//        }
//        catch(Exception exception){
//            System.out.println("Error!");
//        }
//    }
//
//}