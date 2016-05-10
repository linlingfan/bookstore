package com.fjnu.bookstore.cart.web.servlet;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.servlet.BaseServlet;

import com.fjnu.bookstore.book.domain.Book;
import com.fjnu.bookstore.book.service.BookService;
import com.fjnu.bookstore.cart.domian.Cart;
import com.fjnu.bookstore.cart.domian.CartItem;

public class CartServlet extends BaseServlet {
    /**
     * 增加购物条目
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String add(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*
		 * 1. 得到车
		 * 2. 得到条目（得到图书和数量）
		 * 3. 把条目添加到车中
		 */
		/*
		 * 1. 得到车
		 */
        Cart cart = (Cart) request.getSession().getAttribute("cart");
		/*
		 * 表单传递的只有bid和数量
		 * 2. 得到条目
		 *   > 得到图书和数量
		 *   > 先得到图书的bid，然后我们需要通过bid查询数据库得到Book
		 *   > 数量表单中有
		 */
        String bid = request.getParameter("bid");
        //获取图书信息
        Book book = new BookService().findBybid(bid);
        int count = Integer.parseInt(request.getParameter("count"));
        //int count=(Integer) request.getAttribute("count");
        CartItem cartItem = new CartItem();
        cartItem.setBook(book);
        cartItem.setCount(count);
        /**
         * 把条目添加到车中
         */
        cart.add(cartItem);
        return "f:/jsps/cart/list.jsp";
    }

    /**
     * 清空购物条目
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String clear(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /**
         * 获得车，
         * clear车
         */
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        cart.clear();
        return "f:/jsps/cart/list.jsp";
    }

    /**
     * 删除商品条目
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String delete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        /**
         * 获得车，和要删除的bid
         * 删除cart.delete()
         */
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        String bid = request.getParameter("bid");
        cart.delete(bid);

        return "f:/jsps/cart/list.jsp";


    }


}
