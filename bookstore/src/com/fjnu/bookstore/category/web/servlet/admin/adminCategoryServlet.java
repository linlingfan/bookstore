package com.fjnu.bookstore.category.web.servlet.admin;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import com.fjnu.bookstore.category.domain.Category;
import com.fjnu.bookstore.category.service.CategoryService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by lenovo on 2016/3/19.
 */

public class adminCategoryServlet extends BaseServlet {
    private CategoryService categoryService = new CategoryService();

    /**
     * 后台查询所有图书分类
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String findAll(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

                 /*
                    * 1. 调用service方法，得到所有分类
                    * 2. 保存到request域，转发到/adminjsps/admin/category/list.jsp
                */
        request.setAttribute("categoryList", categoryService.findAll());
        return "f:/adminjsps/admin/category/list.jsp";
    }

    /**
     * 后台添加图书分类
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String add1(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*
         * 1. 封装表单数据
		 * 2. 补全：cid
		 * 3. 调用service方法完成添加工作
		 * 4. 调用findAll()
		 */
        Category category= CommonUtils.toBean(request.getParameterMap(),Category.class);
        category.setCid(CommonUtils.uuid());
        categoryService.add(category);
        return findAll(request,response); //这个是Servlet的findAll

    }

    /**
     * 删除分类
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */

    public String delete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /**
         * 获取cid，按cid删除
         * 条用service方法删除
         * 如果抛出异常转发到msg.jsp
         */
        String cid=request.getParameter("cid");
        System.out.println("1111111111111"+cid);
        try {
            categoryService.delete(cid);
            return findAll(request,response);
        } catch (categoryException e) {
            request.setAttribute("msg",e.getMessage());
            return "f:/adminjsps/msg.jsp";
        }

    }

    /**
     * 修改分类前的加载到mod.jsp数据操作
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String loadPre(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /**
         * 获取cid，查询此分类
         * 保存到request并转发到mod.jsp
         */
        String cid=request.getParameter("cid");
        System.out.println("222222222222"+cid);
        request.setAttribute("category", categoryService.load(cid));
        return "f:/adminjsps/admin/category/mod.jsp";
    }

    /**
     * 修改分类
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String edit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //一件封装表单数据
        Category category=CommonUtils.toBean(request.getParameterMap(),Category.class);
        categoryService.edit(category);
        //放回findAll
        return findAll(request,response);
    }


}

