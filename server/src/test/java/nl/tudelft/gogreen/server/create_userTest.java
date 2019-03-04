//package nl.tudelft.gogreen.server;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import org.testng.annotations.Test;
//
//import static org.hibernate.validator.internal.util.Contracts.assertTrue;
//import static org.junit.Assert.*;
//
//public class create_userTest {
//    Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"));
//
//    @Test
//    public void create_userTest() {
//        create_user.create_user('paul','paul',conn);
//        assertTrue(log_in.log_in('paul','paul',conn));
//    }
//}