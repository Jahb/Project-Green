package nl.tudelft.gogreen.server;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Utils {
    private static ResourceBundle resource = ResourceBundle.getBundle("db");

    public static ObjectMapper mapper = new ObjectMapper();

    //    fun <T> HttpServletResponse.writeJson(data: T, errorCode: Int = 200) {
//        this.status = errorCode
//        this.contentType = "application/json"
//        this.outputStream.println(mapper.writeValueAsString(data))
//    }
//
    public static <T> void writeJson(HttpServletResponse response, T data, int error) throws IOException {
        response.setStatus(error);
        response.setContentType("application/json");
        response.getOutputStream().println(mapper.writeValueAsString(data));
    }

    public static <T> void writeJson(HttpServletResponse response, T data) throws IOException {
        writeJson(response, data, 200);
    }

    public static List<Integer> verifyUsersValid(String... usernames) {
        try {
            List<Integer> uids = new ArrayList<>();
            Connection conn = DriverManager.getConnection(
                    resource.getString("Postgresql.datasource.url"),
                    resource.getString("Postgresql.datasource.username"),
                    resource.getString("Postgresql.datasource.password"));
            for (String username : usernames) {
                int otherID = NewFeature.getId(username,conn);
                uids.add(otherID);
            }
            return uids;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;


    }
}
