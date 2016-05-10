package com.fjnu.bookstore.order.web.servlet.admin;

import cn.itcast.servlet.BaseServlet;
import com.fjnu.bookstore.order.service.OrderException;
import com.fjnu.bookstore.order.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.PrivateKey;

/**
 * Created by lenovo on 2016/3/23.
 */
@WebServlet(urlPatterns = "/AdminOrderServlet")
public class AdminOrderServlet extends BaseServlet {
    private OrderService orderService = new OrderService();

    /**
     * 后台查看所有订单
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String findAll(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("orderList", orderService.findAll());
        return "f:/adminjsps/admin/order/list.jsp";

    }

    /**
     * 通过订单状态查找
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String findByState(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //获取state
        int state = Integer.parseInt(request.getParameter("state"));
        request.setAttribute("orderList", orderService.findByState(state));
        return "f:/adminjsps/admin/order/list.jsp";

    }

    /**
     * 后台修改订单状态发货
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String editState(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //获取oid
        String oid = request.getParameter("oid");
        try {
            orderService.adminConfirm(oid);
            request.setAttribute("msg","发货成功！");
        } catch (OrderException e) {
            request.setAttribute("msg",e.getMessage());
        }
        return "f:/jsps/msg.jsp";

    }


}
