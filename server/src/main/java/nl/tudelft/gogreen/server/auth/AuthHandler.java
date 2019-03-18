package nl.tudelft.gogreen.server.auth;

import nl.tudelft.gogreen.server.Utils;
import nl.tudelft.gogreen.shared.MessageHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthHandler implements AuthenticationSuccessHandler, LogoutSuccessHandler, AuthenticationFailureHandler {
    private MessageHolder authSuccess = new MessageHolder<>("Auth success!", true);
    private MessageHolder authFail = new MessageHolder<>("Auth Failed!", false);
    private MessageHolder logoutSuccess = new MessageHolder("Logged out!");

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        Utils.writeJson(response, authFail, 401);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Utils.writeJson(response, authSuccess);
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Utils.writeJson(response, logoutSuccess);
    }
}
