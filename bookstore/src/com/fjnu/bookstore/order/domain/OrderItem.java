package com.fjnu.bookstore.order.domain;

import com.fjnu.bookstore.book.domain.Book;

public class OrderItem {
    private String iid;//订单条目id
    private int count;//数量
    private double subtotal;//小计
    private Order order;//所属订单
    private Book book;// 所要购买的图书

    public String getIid() {
        return iid;
    }

    public int getCount() {
        return count;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public Order getOrder() {
        return order;
    }

    public Book getBook() {
        return book;
    }

    public void setIid(String iid) {
        this.iid = iid;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setBook(Book book) {
        this.book = book;
    }


}
