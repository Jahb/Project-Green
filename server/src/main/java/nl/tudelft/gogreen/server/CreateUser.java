package nl.tudelft.gogreen.server;

import org.springframework.security.crypto.bcrypt.BCrypt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class CreateUser {
    private static ResourceBundle resource = ResourceBundle.getBundle("db");

    /**
     * Method to create a user
     * @param username The name of the user
     * @param password Hash of the password of the user
     * @return returns true if user is created else exception
     * @throws Exception raised when error accessing the database
     */

    public static boolean create_user(String username, String password) throws Exception {
        Connection conn = DriverManager.getConnection(
                resource.getString("Postgresql.datasource.url"),
                resource.getString("Postgresql.datasource.username"),
                resource.getString("Postgresql.datasource.password"));
        int id = getMaxId(conn);
        if (id == -1) id = 0;
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
        String hashpass = "'" + hashed + "'";

        PreparedStatement user = conn.prepareStatement("insert into user_table " +
                "values ( " + id + ",'" + username + "', " + hashpass + ");");
        user.execute();
        PreparedStatement obj = conn.prepareStatement("insert into objective " +
                "values (" + id + ", NULL);");
        obj.execute();
        PreparedStatement hab1 = conn.prepareStatement("insert into initial_habits " +
                "values ( " + id + ", 'smoke', FALSE);");
        hab1.execute();
        PreparedStatement hab2 = conn.prepareStatement("insert into initial_habits " +
                "values (" + id + ", 'recycling person', FALSE); ");
        hab2.execute();
        PreparedStatement hab3 = conn.prepareStatement("insert into initial_habits " +
                "values(" + id + ", 'use of recycle paper', FALSE);");
        hab3.execute();
        PreparedStatement hab4 = conn.prepareStatement("insert into initial_habits " +
                "values (" + id + ", 'eco-friendly clothes usage', FALSE);");
        hab4.execute();
        PreparedStatement streak = conn.prepareStatement("insert into streak " +
                "values (" + id + ", current_date  ,   1  );");
        streak.execute();
        PreparedStatement userpoints = conn.prepareStatement("insert into user_points " +
                "values (" + id + ",  0  ,   0  ,   0  ,   0  ,   0  );");
        userpoints.execute();
        return true;
    }

    /**
     * Method which returns the  
     * @param conn
     * @return
     * @throws Exception
     */
    public static int getMaxId(Connection conn) throws Exception {

        PreparedStatement stmt0 = conn.prepareStatement("select user_id " +
                "from user_table order by user_id desc limit 1;");
        ResultSet rs0 = stmt0.executeQuery();
        int id = -1;
        while (rs0.next()) {
            id = rs0.getInt(1);

            id = id + 1;
        }

        return id;


    }

    public static boolean delete_user(int id, Connection conn) throws Exception {

        PreparedStatement delObjective = conn.prepareStatement("delete from objective " +
                "where user_id =" + id + ";");
        PreparedStatement delHabits = conn.prepareStatement("delete from initial_habits " +
                "where user_id =" + id + "; ");
        PreparedStatement delStreak = conn.prepareStatement("delete from streak " +
                "where user_id =" + id + "; ");
        PreparedStatement delUserPoints = conn.prepareStatement("delete from user_points " +
                "where user_id =" + id + "; ");
        PreparedStatement delUser_table = conn.prepareStatement("delete from user_table " +
                "where user_id =" + id + "; ");
        delObjective.execute();
        delHabits.execute();
        delStreak.execute();
        delUserPoints.execute();
        EventsMain.deleteAllEvents(id, conn);
        EventsMain.deleteAllAtendance(id, conn);
        delUser_table.execute();
        return true;

    }

    public static boolean deleteAllUsers(Connection conn) throws Exception {
        boolean loop = true;
        int id = -1;
        while (loop) {
            id = getMaxId(conn);
            if (id != -1) id--;
            System.out.println("The id is: " + id);
            if (id != -1) {
                delete_user(id, conn);
            } else {
                loop = false;
            }
        }
        return true;
    }
}