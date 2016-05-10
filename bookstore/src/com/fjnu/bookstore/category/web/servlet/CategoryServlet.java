package com.fjnu.bookstore.category.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.servlet.BaseServlet;

import com.fjnu.bookstore.category.service.CategoryService;


public class CategoryServlet extends BaseServlet {
    /**
     * 查询所有图书类型
     */
    private CategoryService categoryService = new CategoryService();

    public String findAll(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("categoryList", categoryService.findAll());

        return "f:/jsps/left.jsp";
    }
}
