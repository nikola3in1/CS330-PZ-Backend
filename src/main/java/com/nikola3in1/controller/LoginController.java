package com.nikola3in1.controller;

import com.nikola3in1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("test")
    ResponseEntity<Map<String, String>> test() {
        System.out.println("CONTROLLER IS CALLED");
        return new ResponseEntity<>(new HashMap<String, String>() {{
            this.put("status", "ok");
        }}, HttpStatus.OK);
    }


    @GetMapping("authenticate")
    ResponseEntity<String> auth(HttpServletRequest request) {
        System.out.println("User principal :" + request.getUserPrincipal().toString());
        return new ResponseEntity<>("authenticated", HttpStatus.OK);
    }

    @PostMapping(value = "login")
    ResponseEntity<String> login() {
        return new ResponseEntity<>("asd", HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping("private")
    ResponseEntity<String> secret() {
        return new ResponseEntity<>("access granted", HttpStatus.OK);
    }
}