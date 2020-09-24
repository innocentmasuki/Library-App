package com.example.library;

public class Books {
    String title, author, cover;

    public Books(String title, String author, String cover) {
        this.setTitle(title);
        this.setAuthor(author);
        this.setCover(cover);
    }

    public  Books(){

    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
