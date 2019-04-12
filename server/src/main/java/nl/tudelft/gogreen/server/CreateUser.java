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
     * @param password Unhashed password of the user
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
        String hashpass = BCrypt.hashpw(password, BCrypt.gensalt());


        PreparedStatement user = conn.prepareStatement(resource.getString("qInsertUser"));
        user.setInt(1, id);
        user.setString(2, username);
        user.setString(3, hashpass);
        user.execute();
        PreparedStatement obj = conn.prepareStatement(resource.getString("qInsertObjective"));
        obj.setInt(1, id);
        obj.setNull(2, Types.VARCHAR);
        obj.execute();
        PreparedStatement hab1 = conn.prepareStatement(resource.getString("qInsertHabits"));
        hab1.setInt(1, id);
        hab1.setString(2, "smoke");
        hab1.setBoolean(3, false);
        hab1.execute();
        PreparedStatement hab2 = conn.prepareStatement(resource.getString("qInsertHabits2"));
        hab2.setInt(1, id);
        hab2.setString(2, "recycling person");
        hab2.setBoolean(3, false);
        hab2.execute();
        PreparedStatement hab3 = conn.prepareStatement(resource.getString("qInsertHabits3"));
        hab3.setInt(1, id);
        hab3.setString(2, "use of recycle paper");
        hab3.setBoolean(3, false);
        hab3.execute();
        PreparedStatement hab4 = conn.prepareStatement(resource.getString("qInsertHabits4"));
        hab4.setInt(1, id);
        hab4.setString(2, "eco-friendly clothes usage");
        hab4.setBoolean(3, false);
        hab4.execute();
        PreparedStatement streak = conn.prepareStatement(resource.getString("qInsertStreakk"));
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        streak.setInt(1, id);
        streak.setNull(2, Types.DATE);
        streak.setInt(3, 0);
        streak.execute();
        PreparedStatement userpoints =
                conn.prepareStatement(resource.getString("qInsertUserPoints"));
        userpoints.setInt(1, id);
        userpoints.setInt(2, 0);
        userpoints.setInt(3, 0);
        userpoints.setInt(4, 0);
        userpoints.setInt(5, 0);
        userpoints.setInt(6, 0);
        userpoints.execute();
        PreparedStatement userHistory
                = conn.prepareStatement(resource.getString("qInsertHistory0"));
        userHistory.setInt(1,id);

        PreparedStatement insertquiz = conn.prepareStatement(resource.getString("qQuizData"));
        insertquiz.setInt(1,id);
        insertquiz.setInt(2,10);
        insertquiz.setInt(3,10);
        insertquiz.setBoolean(4,false);
        insertquiz.setInt(5,10);
        insertquiz.setInt(6,10);


        conn.close();
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

        PreparedStatement stmt0 = conn.prepareStatement(resource.getString("qGetMaxId2"));
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
     *
     * @param id   of the user to be deleted
     * @param conn Connection to the database
     * @return Returns true if the user is correctly delete it
     * @throws Exception raises when error accessing the database
     */


    public static boolean delete_user(int id, Connection conn) throws Exception {



        PreparedStatement delObjective =
                conn.prepareStatement(resource.getString("qDeleteObjective"));
        delObjective.setInt(1, id);
        delObjective.execute();

        PreparedStatement delHabits =
                conn.prepareStatement(resource.getString("qDeleteHabits"));
        delHabits.setInt(1, id);
        delHabits.execute();

        PreparedStatement delStreak =
                conn.prepareStatement(resource.getString("qDeleteStreak"));
        delStreak.setInt(1, id);
        delStreak.execute();

        PreparedStatement delUserPoints =
                conn.prepareStatement(resource.getString("qDeleteUserPoints"));
        delUserPoints.setInt(1, id);
        delUserPoints.execute();

        PreparedStatement delFeaturesHistory =
                conn.prepareStatement(resource.getString("qDeleteFeaturesHistory"));
        delFeaturesHistory.setInt(1, id);
        delFeaturesHistory.execute();

        PreparedStatement delQuizTable =
                conn.prepareStatement(resource.getString("qDeleteQuizTable"));
        delQuizTable.setInt(1, id);
        delQuizTable.execute();

        PreparedStatement delUserHistory =
                conn.prepareStatement(resource.getString("qDeleteUserHistory"));
        delUserHistory.setInt(1, id);
        delUserHistory.execute();

        EventsMain.deleteAllAtendance(id, conn);
        EventsMain.deleteAllEvents(id, conn);

        Following.deleteAllFollows(id, conn);

        PreparedStatement delUserTable =
                conn.prepareStatement(resource.getString("qDeleteUserTable"));
        delUserTable.setInt(1, id);
        delUserTable.execute();

        return true;

    }


    /**
     * Method which deletes all current users of the database.
     *
     * @param conn Connection to the database
     * @return Returns true if all users are correctly delete it
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