package com.jacksanger.mhassistant.security.models;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;

import com.jacksanger.mhassistant.security.annotations.FieldMatch;

/*
 * Java class to store user registration information
 * Contains getters and setters for all class variables
 */

@FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match")
public class UserRegistrationDtO {


    @NotEmpty
    private String password;

    @NotEmpty
    private String confirmPassword;

    @NotEmpty
    private String userName;

    @AssertTrue
    private Boolean terms;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean getTerms() {
        return terms;
    }

    public void setTerms(Boolean terms) {
        this.terms = terms;
    }
}
