package com.nikola3in1.config.model;

import java.sql.Date;

public class GoogleValidateTokenResponse {
    private String iss;
    private String azp;
    //User id
    private String sub;
    //Client-id
    private String aud;
    //User details
    private String name;
    private String picture;
    private String given_name;
    private String family_name;
    private String locale;
    //Only if granted
    private String email;
    //Other
    private Integer iat;
    private Date exp;
    private String alg;
    private String kid;
    private String typ;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIss() {
        return iss;
    }

    public void setIss(String iss) {
        this.iss = iss;
    }

    public String getAzp() {
        return azp;
    }

    public void setAzp(String azp) {
        this.azp = azp;
    }

    public String getClientAppId() {
        return aud;
    }

    public void setAud(String aud) {
        this.aud = aud;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getGiven_name() {
        return given_name;
    }

    public void setGiven_name(String given_name) {
        this.given_name = given_name;
    }

    public String getFamily_name() {
        return family_name;
    }

    public void setFamily_name(String family_name) {
        this.family_name = family_name;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public Integer getIat() {
        return iat;
    }

    public void setIat(Integer iat) {
        this.iat = iat;
    }

    public Date getExp() {
        return exp;
    }

    public void setExp(Date exp) {
        this.exp = exp;
    }

    public String getAlg() {
        return alg;
    }

    public void setAlg(String alg) {
        this.alg = alg;
    }

    public String getKid() {
        return kid;
    }

    public void setKid(String kid) {
        this.kid = kid;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    @Override
    public String toString() {
        return "GoogleValidateTokenResponse{" +
                "iss='" + iss + '\'' +
                ", azp='" + azp + '\'' +
                ", sub='" + sub + '\'' +
                ", aud='" + aud + '\'' +
                ", name='" + name + '\'' +
                ", picture='" + picture + '\'' +
                ", given_name='" + given_name + '\'' +
                ", family_name='" + family_name + '\'' +
                ", locale='" + locale + '\'' +
                ", iat=" + iat +
                ", exp=" + exp +
                ", alg='" + alg + '\'' +
                ", kid='" + kid + '\'' +
                ", typ='" + typ + '\'' +
                '}';
    }
}
