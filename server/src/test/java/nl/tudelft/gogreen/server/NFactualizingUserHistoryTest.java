package nl.tudelft.gogreen.server;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

public class NFactualizingUserHistoryTest {

    private static ResourceBundle resource = ResourceBundle.getBundle("db");

    @Test
    public void actualizingUserPoints(){
        try(Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"),
                resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"))) {

            int id = NewFeature.getId("MJ", conn);

            //TO IMPLEMENT

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Before
    public void createOnlyUser() throws Exception {

            CreateUser.create_user("MJ","MJ");



    }

    @After
    public void deleteUser() throws Exception{
        try(Connection conn = DriverManager.getConnection(resource.getString("Postgresql.datasource.url"), resource.getString("Postgresql.datasource.username"), resource.getString("Postgresql.datasource.password"))) {
            CreateUser.delete_user(NewFeature.getId("MJ",conn),conn);
        }
                catch(Exception e){
                    System.out.println(e.getMessage());
            }
            }
    }

