package com.example.library;

public class Notification {
    String nBookCover, nBookTitle, nBookAuthor, nrequestedBy, nId, nStatus, nIsbn, approvedBy;

    public Notification(String bookCover, String bookTitle, String bookAuthor, String requestedBy, String id, String status, String isbn, String approvedby) {

        this.setnBookAuthor(bookAuthor);
        this.setnBookTitle(bookTitle);
        this.setnBookCover(bookCover);
        this.setNrequestedBy(requestedBy);
        this.setnId(id);
        this.setnStatus(status);
        this.setnIsbn(isbn);
        this.setApprovedBy(approvedby);

    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approveBy) {
        this.approvedBy = approveBy;
    }

    public String getnIsbn() {
        return nIsbn;
    }

    public void setnIsbn(String isbn) {
        this.nIsbn = isbn;
    }

    public String getnId() {
        return nId;
    }

    public void setnId(String id) {
        this.nId = id;
    }

    public String getnStatus() {
        return nStatus;
    }

    public void setnStatus(String status) {
        this.nStatus = status;
    }

    public String getnBookCover() {
        return nBookCover;
    }

    public void setnBookCover(String bookCover) {
        this.nBookCover = bookCover;
    }

    public String getnBookTitle() {
        return nBookTitle;
    }

    public void setnBookTitle(String bookTitle) {
        this.nBookTitle = bookTitle;
    }

    public String getnBookAuthor() {
        return nBookAuthor;
    }

    public void setnBookAuthor(String bookAuthor) {
        this.nBookAuthor = bookAuthor;
    }

    public String getNrequestedBy() {
        return nrequestedBy;
    }

    public void setNrequestedBy(String requestedBy) {
        this.nrequestedBy = requestedBy;
    }
}
