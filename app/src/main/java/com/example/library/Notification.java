package com.example.library;

public class Notification {
    String nBookCover, nBookTitle, nBookAuthor, nrequestedBy;

    public Notification(String nBookCover, String nBookTitle, String nBookAuthor, String nrequestedBy) {
        this.nBookCover = nBookCover;
        this.nBookTitle = nBookTitle;
        this.nBookAuthor = nBookAuthor;
        this.nrequestedBy = nrequestedBy;
    }

    public String getnBookCover() {
        return nBookCover;
    }

    public void setnBookCover(String nBookCover) {
        this.nBookCover = nBookCover;
    }

    public String getnBookTitle() {
        return nBookTitle;
    }

    public void setnBookTitle(String nBookTitle) {
        this.nBookTitle = nBookTitle;
    }

    public String getnBookAuthor() {
        return nBookAuthor;
    }

    public void setnBookAuthor(String nBookAuthor) {
        this.nBookAuthor = nBookAuthor;
    }

    public String getNrequestedBy() {
        return nrequestedBy;
    }

    public void setNrequestedBy(String nrequestedBy) {
        this.nrequestedBy = nrequestedBy;
    }
}
