package com.fjnu.bookstore.category.domain;

public class Category {
    private String cid;
    private String cname;

    public String getCid() {
        return cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    @Override
    public String toString() {
        return "Category [cid=" + cid + ", cname=" + cname + "]";
    }
}
