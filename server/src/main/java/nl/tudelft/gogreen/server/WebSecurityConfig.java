package nl.tudelft.gogreen.server;

import nl.tudelft.gogreen.server.auth.AuthHandler;
import nl.tudelft.gogreen.server.auth.LogIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthHandler responses;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().antMatcher("/**")
                .formLogin().successHandler(responses).failureHandler(responses)
                .and().logout().logoutSuccessHandler(responses);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(new AuthStuff());
    }

    @Component
    public static class AuthStuff implements AuthenticationProvider {

        @Override
        public Authentication authenticate(Authentication authentication)
                throws AuthenticationException {
            String name = authentication.getName();
            String pass = authentication.getCredentials().toString();
            boolean check = false;
            try {
                check = LogIn.log_in(name,pass);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (!check) return null;
            List<GrantedAuthority> auths = new ArrayList<>();
            if (name.equals("admin")) {
                auths.add(new SimpleGrantedAuthority("ADMIN"));
            }
            auths.add(new SimpleGrantedAuthority("USER"));
            return new UsernamePasswordAuthenticationToken(name, pass, auths);

        }

        @Override
        public boolean supports(Class<?> authentication) {
            return authentication.equals(UsernamePasswordAuthenticationToken.class);
        }
    }
}
