package nl.tudelft.gogreen.server;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Homepage {

    public String getHomeString() {
        return "<h1>Home!</h1>";
    }

    @RequestMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String home() {
        return getHomeString();
    }
}
