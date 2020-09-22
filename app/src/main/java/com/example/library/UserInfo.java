package com.example.library;

public class UserInfo {
    private String Name, Email, Password, Category;

    public UserInfo(String name, String email, String password, String category) {
        this.setName(name);
        this.setEmail(email);
        this.setPassword(password);
        this.setCategory(category);

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }
}
