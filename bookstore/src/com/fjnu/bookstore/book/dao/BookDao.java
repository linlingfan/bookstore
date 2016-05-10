package com.fjnu.bookstore.book.dao;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;
import com.fjnu.bookstore.book.domain.Book;
import com.fjnu.bookstore.category.domain.Category;
import com.fjnu.bookstore.order.domain.Order;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class BookDao {
    private QueryRunner qr = new TxQueryRunner();

    /**
     * 查寻所有的图书
     */

    public List<Book> findAll() {

        String sql = "select * from book where del=false";
        try {
            return qr.query(sql, new BeanListHandler<Book>(Book.class));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 通过分类查询图书
     *
     * @param cid
     * @return
     */
    public List<Book> findByCategory(String cid) {
        String sql = "select * from book where cid=? and del=false";

        try {
            return qr.query(sql, new BeanListHandler<Book>(Book.class), cid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 通过图书id查询图书
     *
     * @param bid
     * @return
     */
    public Book findBybid(String bid) {
        try {
            String sql = "select * from book where bid=?";
            Map<String, Object> map = qr.query(sql, new MapHandler(), bid);
            Category category = CommonUtils.toBean(map, Category.class);
            Book book = CommonUtils.toBean(map, Book.class);
            book.setCategory(category);
            return book;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取指定分类下图书的数量!!!!!!
     *
     * @param cid
     * @return
     */
    public int getCountBycid(String cid) {
        try {
            String sql = "select count(*) from book where cid=?";
            Number cnt = (Number) qr.query(sql, new ScalarHandler(), cid);
            return cnt.intValue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 添加图书的功能
     *
     * @param book
     */
    public void add(Book book) {
        try {
            String sql = "insert into book values(?,?,?,?,?,?)";
            Object[] params = {book.getBid(), book.getBname(), book.getPrice(), book.getAuthor(),
                    book.getImage(), book.getCategory().getCid()};

            qr.update(sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    /**
     * 删除图书
     * @param bid
     */
    public void delete(String bid) {

        try {
            String sql = "update book set del=true where bid=?";
            qr.update(sql, bid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 修改图书
     * @param book
     */
    public void edit(Book book) {
        try {
            String sql = "update book set bname=?, price=?,author=?, image=?, cid=? where bid=?";
            Object[] params = {book.getBname(), book.getPrice(), book.getAuthor(),
                    book.getImage(), book.getCategory().getCid(),book.getBid()};
            qr.update(sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
