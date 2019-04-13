package nl.tudelft.gogreen.server.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.tudelft.gogreen.shared.MessageHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class AuthHandler implements AuthenticationSuccessHandler,
        LogoutSuccessHandler, AuthenticationFailureHandler {
    private MessageHolder authSuccess = new MessageHolder<>("Auth success!", true);
    private MessageHolder authFail = new MessageHolder<>("Auth Failed!", false);
    private MessageHolder logoutSuccess = new MessageHolder("Logged out!");
    private ObjectMapper mapper;

    public AuthHandler(@Autowired ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        response.setStatus(401);
        response.setContentType("application/json");
        response.getOutputStream().println(mapper.writeValueAsString(authFail));
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        response.setStatus(200);
        response.setContentType("application/json");
        response.getOutputStream().println(mapper.writeValueAsString(authSuccess));
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response,
                                Authentication authentication) throws IOException {
        response.setStatus(200);
        response.setContentType("application/json");
        response.getOutputStream().println(mapper.writeValueAsString(logoutSuccess));
    }
}
