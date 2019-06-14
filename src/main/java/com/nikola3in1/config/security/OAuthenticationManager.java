package com.nikola3in1.config.security;

import com.nikola3in1.config.model.GoogleValidateTokenResponse;
import com.nikola3in1.model.User;
import com.nikola3in1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class OAuthenticationManager implements AuthenticationManager {

    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getPrincipal() + "";
        GoogleValidateTokenResponse credentials = (GoogleValidateTokenResponse) authentication.getCredentials();

        System.out.println("username "+username);
        System.out.println("manager is called");

        System.out.println("users: "+Arrays.toString(userService.findAll().toArray()));
        User user = userService.findByUsername(username);
        if (user == null) {
            //new user
            user = new User();
            user.setUsername(username);
            user.setPassword(""); //Temporary
            user.setActive(true);
            user.setEmail("");
            //Check if available
            if (credentials.getEmail() != null && !credentials.getEmail().isEmpty()) {
                user.setEmail(credentials.getEmail());
            }
            if (credentials.getPicture() != null && !credentials.getPicture().isEmpty()) {
                user.setPicture(credentials.getPicture());
            }
            if (credentials.getGiven_name() != null && !credentials.getGiven_name().isEmpty()) {
                user.setFirstName(credentials.getGiven_name());
            }
            if (credentials.getFamily_name() != null && !credentials.getFamily_name().isEmpty()) {
                user.setLastName(credentials.getFamily_name());
            }

            userService.save(user);
        }
        user.setActive(true);

        List<GrantedAuthority> grantedAuths = new ArrayList<>();
        grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new UsernamePasswordAuthenticationToken(username, "", grantedAuths);
    }
}
