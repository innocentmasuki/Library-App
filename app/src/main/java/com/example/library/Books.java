package com.example.library;

public class Books {
    String title, author, cover, isbn, Category, uploadedBy, available, descript ,requests;

    public Books(String title, String author, String cover, String isbn, String Category, String uploadedBy, String available, String descript, String requests) {
        this.setTitle(title);
        this.setAuthor(author);
        this.setCover(cover);
        this.setIsbn(isbn);
        this.setCategory(Category);
        this.setUploadedBy(uploadedBy);
        this.setAvailable(available);
        this.setDescript(descript);
        this.setRequests(requests);
    }

    public String getRequests() {
        return requests;
    }

    public void setRequests(String requests) {
        this.requests = requests;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
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
