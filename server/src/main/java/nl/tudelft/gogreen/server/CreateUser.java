package nl.tudelft.gogreen.server;

import org.springframework.security.crypto.bcrypt.BCrypt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class CreateUser {
    private static ResourceBundle resource = ResourceBundle.getBundle("db");

    /**
     * Method to create a user.
     *
     * @param username The name of the user
     * @param password  Unhashed password of the user
     * @return returns true if user is created else exception
     * @throws Exception raises when error accessing the database
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
                "values ( ?, ? ,?  );");
        user.setInt(1, id);
        user.setString(2,username);
        user.setString(3,hashpass);
        user.execute();
        PreparedStatement obj = conn.prepareStatement("insert into objective values (?, ?);");
        obj.setInt(1, id);
        obj.setNull(2, Types.VARCHAR);
        obj.execute();
        PreparedStatement hab1 = conn.prepareStatement("insert into initial_habits " +
                "values (?,?,?);");
        hab1.setInt(1, id);
        hab1.setString(2,"smoke");
        hab1.setBoolean(3,false);
        hab1.execute();
        PreparedStatement hab2 = conn.prepareStatement("insert into initial_habits " +
                "values (?,? ,?); ");
        hab2.setInt(1, id);
        hab2.setString(2,"recycling person");
        hab2.setBoolean(3,false);
        hab2.execute();
        PreparedStatement hab3 = conn.prepareStatement("insert into initial_habits " +
                "values(?,?,?);");
        hab3.setInt(1, id);
        hab3.setString(2,"use of recycle paper");
        hab3.setBoolean(3,false);
        hab3.execute();
        PreparedStatement hab4 = conn.prepareStatement("insert into initial_habits " +
                "values (?,?,?)");
        hab4.setInt(1, id);
        hab4.setString(2,"eco-friendly clothes usage");
        hab4.setBoolean(3,false);
        hab4.execute();
        PreparedStatement streak = conn.prepareStatement("insert into streak " +
                "values (?, ?,?);");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String today = dateFormat.format(date);
        streak.setInt(1, id);
        streak.setDate(2, java.sql.Date.valueOf(today));
        streak.setInt(3,1);
        streak.execute();
        PreparedStatement userpoints = conn.prepareStatement("insert into user_points " +
                "values (?,?,?,?,?,?)");
        userpoints.setInt(1, id);
        userpoints.setInt(2, 0);
        userpoints.setInt(3, 0);
        userpoints.setInt(4, 0);
        userpoints.setInt(5, 0);
        userpoints.setInt(6, 0);
        userpoints.execute();
        return true;
    }

    /**
     * Method which returns the next id for events available.
     *
     * @param conn Connection to the database
     * @return returns the id
     * @throws Exception raises when error accessing the database
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

    /**
     * Method which deletes the user with given id.
     * @param id of the user to be deleted
     * @param conn Connection to the database
     * @return Returns true if the user is correctly delete it
     * @throws Exception raises when error accessing the database
     */
    public static boolean delete_user(int id, Connection conn) throws Exception {

        PreparedStatement delObjective = conn.prepareStatement("delete from objective " +
                "where user_id =" + id + ";");
        delObjective.execute();
        PreparedStatement delHabits = conn.prepareStatement("delete from initial_habits " +
                "where user_id =" + id + "; ");
        delHabits.execute();
        PreparedStatement delStreak = conn.prepareStatement("delete from streak " +
                "where user_id =" + id + "; ");
        delStreak.execute();
        PreparedStatement delUserPoints = conn.prepareStatement("delete from user_points " +
                "where user_id =" + id + "; ");
        delUserPoints.execute();
        PreparedStatement delUserTable = conn.prepareStatement("delete from user_table " +
                "where user_id =" + id + "; ");
        EventsMain.deleteAllEvents(id, conn);
        EventsMain.deleteAllAtendance(id, conn);
        delUserTable.execute();
        return true;
    }

    /**
     * Method which deletes all current users of the database.
     * @param conn Connection to the database
     * @return  Returns true if all users are correctly delete it
     * @throws Exception raises when error accessing the database
     */
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