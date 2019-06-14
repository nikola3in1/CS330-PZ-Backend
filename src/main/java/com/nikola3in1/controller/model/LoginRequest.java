package com.nikola3in1.controller.model;

public class LoginRequest {
    private String idToken;

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "idToken='" + idToken + '\'' +
                '}';
    }
}
