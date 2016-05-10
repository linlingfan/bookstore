package com.fjnu.bookstore.book.web.servlet.admin;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import com.fjnu.bookstore.book.domain.Book;
import com.fjnu.bookstore.book.service.BookService;
import com.fjnu.bookstore.category.domain.Category;
import com.fjnu.bookstore.category.service.CategoryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.PublicKey;

/**
 * Created by lenovo on 2016/3/20.
 */

/**
 * 后台管理图书
 */
@WebServlet(urlPatterns = "/AdminBookServlet")
public class AdminBookServlet extends BaseServlet {
    private BookService bookService = new BookService();
    private CategoryService categoryService = new CategoryService();

    /**
     * 加载所有分类到add.jsp的分类选择框里
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String addCate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*
		 * 查询所有分类，保存到request，转发到add.jsp
		 * add.jsp中把所有的分类使用下拉列表显示在表单中
		 */
        request.setAttribute("categoryList", categoryService.findAll());
        return "f:/adminjsps/admin/book/add.jsp";
    }


    /**
     * 查找所有图书
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String findAll(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("bookList", bookService.findAll());
        return "f:/adminjsps/admin/book/list.jsp";
    }

    /**
     * 通过id查到当前图书的具体信息并加载
     * (查看所有图书信息的加载方法)
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String load(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String bid = request.getParameter("bid");
        request.setAttribute("book", bookService.findBybid(bid));
        //将图书分类信息保存
        request.setAttribute("categoryList", categoryService.findAll());
        return "f:/adminjsps/admin/book/desc.jsp";
    }

    /**
     * 删除图书（其实就是把图书屏蔽将delete的值设为true）
     * 并不是真正意义上的删除
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String delete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //获取bid
        String bid = request.getParameter("bid");
        bookService.delete(bid);
        return findAll(request, response);
    }

    /**
     * 修改图书
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String edit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //一键封装修改后的表单数据到book里，将分类也一键封装到category里
        Book book = CommonUtils.toBean(request.getParameterMap(), Book.class);
        Category category = CommonUtils.toBean(request.getParameterMap(), Category.class);

        //将category set到book里
        book.setCategory(category);
        bookService.edit(book);
        return findAll(request, response);
    }

}
