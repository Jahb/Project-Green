package nl.tudelft.gogreen.server;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Utils {


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

}
