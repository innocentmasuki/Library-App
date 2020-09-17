package com.example.library;

public class UserProfile {
    private String fullname, email, password, category;

    public UserProfile(String fullname, String email, String password, String category) {
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.category = category;
    }
    public UserProfile(){

    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
