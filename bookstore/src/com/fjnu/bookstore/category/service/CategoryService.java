package com.fjnu.bookstore.category.service;

import java.util.List;

import com.fjnu.bookstore.book.dao.BookDao;
import com.fjnu.bookstore.category.dao.CategoryDao;
import com.fjnu.bookstore.category.domain.Category;
import com.fjnu.bookstore.category.web.servlet.admin.categoryException;


public class CategoryService {

    private CategoryDao categoryDao = new CategoryDao();
    private BookDao bookDao = new BookDao();

    /**
     * 业务层查询所有图书分类
     *
     * @return
     */
    public List<Category> findAll() {

        return categoryDao.findAll();
    }


    public void add(Category category) {
        categoryDao.add(category);
    }

    /**
     * 删除分类
     * 要判断此类图书下的本书
     * 如果为0删除，不是抛出异常
     *
     * @param cid
     */

    public void delete(String cid) throws categoryException {

        int count = bookDao.getCountBycid(cid);
        if (count > 0)
            throw new categoryException("该类图书下还有图书不能删除");
        categoryDao.delete(cid);
    }

    /**
     * 加载要修改的分类
     *
     * @param cid
     * @return
     */
    public Category load(String cid) {
        return categoryDao.load(cid);
    }

    /**
     * 修改分类
     *
     * @param category
     */
    public void edit(Category category) {
        categoryDao.edit(category);
    }

}
