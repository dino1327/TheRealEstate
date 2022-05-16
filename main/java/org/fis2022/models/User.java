package org.fis2022.models;

import org.fis2022.exceptions.InvalidCredentials;

import java.util.Date;

public class User {
    private String username;
    private String password;
    private String role;

    public User() {
    }

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
    
    @Override
    public boolean equals(Object o) {

    }

    @Override
    public int hashCode() {
    
    }

    @Override
    public String toString() {

    }
}
