package com.lemonlights.lemon.Model;

public class User {

    private String name;
    private String Password;

    public User(){

    }

    public User(String name, String password) {
        this.name = name;
        Password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
