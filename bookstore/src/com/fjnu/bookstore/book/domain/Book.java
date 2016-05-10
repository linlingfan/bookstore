package com.fjnu.bookstore.book.domain;

import com.fjnu.bookstore.category.domain.Category;

public class Book {
    private String bid;
    private String bname;
    private double price;
    private String author;
    private String image;
    private Category category;
    private boolean del;

    public boolean isDel() {
        return del;
    }

    public void setDel(boolean del) {
        this.del = del;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getBid() {
        return bid;
    }

    public String getBname() {
        return bname;
    }

    public double getPrice() {
        return price;
    }

    public String getAuthor() {
        return author;
    }

    public String getImage() {
        return image;
    }


    public void setBid(String bid) {
        this.bid = bid;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setImage(String image) {
        this.image = image;
    }


    @Override
    public String toString() {
        return "Book [bid=" + bid + ", bname=" + bname + ", price="
                + price + ", author=" + author + ", image=" + image + ", category="
                + category + "]";
    }

}
