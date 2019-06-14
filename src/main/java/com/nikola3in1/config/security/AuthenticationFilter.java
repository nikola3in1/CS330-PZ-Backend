package com.nikola3in1.config.security;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nikola3in1.config.model.GoogleValidateTokenResponse;
import com.nikola3in1.config.model.SecurityConstants;
import com.nikola3in1.model.Role;
import com.nikola3in1.model.User;
import com.nikola3in1.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    //TODO: Can't autowire in a non-spring managed class!
    private UserService userService;

    private AuthenticationManager authenticationManager;

    private final String CLIENT_APP_ID;

    AuthenticationFilter(AuthenticationManager authenticationManager, UserService userService, String client_id) {
        this.authenticationManager = authenticationManager;
        this.CLIENT_APP_ID = client_id;
        setFilterProcessesUrl(SecurityConstants.AUTH_LOGIN_URL);
        this.userService = userService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        String idTokenString = parseIdToken(req);

        if (!idTokenString.isEmpty()) {
            // Call Google Sign-in to validate idToken
            GoogleValidateTokenResponse response = validateToken(idTokenString);
            if (response != null && response.getClientAppId().equals(CLIENT_APP_ID)) {
                System.out.println("Client is authenticated " + response.getName() + ", exp date: " + response.getExp());
                String username = response.getGiven_name().toLowerCase() + response.getSub();

                return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, response));
            }
        }

        throw new RuntimeException("Invalid token");
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult){
        String username = (String) authResult.getPrincipal();

        User user = userService.findByUsername(username);
        System.out.println("successful authentication");
        System.out.println("principal " + username);

        Set<GrantedAuthority> roles = new HashSet<>(authResult.getAuthorities());

        byte[] signingKey = SecurityConstants.JWT_SECRET.getBytes();

        String token = Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
                .setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
                .setIssuer(SecurityConstants.TOKEN_ISSUER)
                .setAudience(SecurityConstants.TOKEN_AUDIENCE)
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + 864000000))
                .claim("rol", roles)
                .compact();

        //Setting token
        user.setToken(token);

        response.setStatus(HttpServletResponse.SC_OK);
        response.addHeader(SecurityConstants.TOKEN_HEADER, SecurityConstants.TOKEN_PREFIX + token);
    }


    private String parseIdToken(HttpServletRequest request) {
        String idTokenString = "";

        idTokenString = request.getParameter("idToken");
        if (idTokenString != null) {
            System.out.println(idTokenString);
        } else {
            try {
                if (request.getParameterMap().keySet().toArray().length > 0) {
                    String jsonString = (String) request.getParameterMap().keySet().toArray()[0];
                    ObjectMapper mapper = new ObjectMapper();
                    System.out.println("JSON STRING: " + jsonString);
                    JsonNode acutalObj = mapper.readTree(jsonString);
                    idTokenString = acutalObj.get("idToken").toString();
                    idTokenString = idTokenString.substring(1, idTokenString.length() - 1);
                    System.out.println(idTokenString);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return idTokenString;
    }

    private GoogleValidateTokenResponse validateToken(String idToken) {

        RestTemplate restTemplate = new RestTemplate();
        String url = SecurityConstants.GOOGLE_API_URL;
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("id_token", idToken);

        try {
            ResponseEntity<GoogleValidateTokenResponse> googleApiResponse = restTemplate.getForEntity(builder.toUriString(),
                    GoogleValidateTokenResponse.class);
            return googleApiResponse.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
