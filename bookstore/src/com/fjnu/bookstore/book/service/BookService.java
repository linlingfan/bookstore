package com.fjnu.bookstore.book.service;

import java.util.List;

import com.fjnu.bookstore.book.dao.BookDao;
import com.fjnu.bookstore.book.domain.Book;

public class BookService {

    private BookDao bookDao = new BookDao();

    /**
     * 查询所有图书
     *
     * @return
     */
    public List<Book> findAll() {

        return bookDao.findAll();

    }

    /**
     * 通过分类查询图书
     *
     * @param cid
     * @return
     */
    public List<Book> findByCategory(String cid) {
        return bookDao.findByCategory(cid);

    }

    /**
     * 通过图书Id查询图书
     *
     * @param bid
     * @return
     */
    public Book findBybid(String bid) {

        return bookDao.findBybid(bid);
    }


    public void add(Book book) {
        bookDao.add(book);

    }

    /**
     * 删除图书
     *
     * @param bid
     */
    public void delete(String bid) {
        bookDao.delete(bid);
    }

    /**
     * 修改图书
     *
     * @param book
     */
    public void edit(Book book) {
        bookDao.edit(book);

    }
}
