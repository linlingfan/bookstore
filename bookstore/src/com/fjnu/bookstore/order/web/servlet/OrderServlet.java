package com.fjnu.bookstore.order.web.servlet;


import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import com.fjnu.bookstore.cart.domian.Cart;
import com.fjnu.bookstore.cart.domian.CartItem;
import com.fjnu.bookstore.order.domain.Order;
import com.fjnu.bookstore.order.domain.OrderItem;
import com.fjnu.bookstore.order.service.OrderException;
import com.fjnu.bookstore.order.service.OrderService;
import com.fjnu.bookstore.user.domain.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderServlet extends BaseServlet {
    private OrderService orderService = new OrderService();

    /**
     * 添加订单add（）
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
         * 1. 从session中得到cart
		 * 2. 使用cart生成Order对象
		 * 3. 调用service方法完成添加订单
		 * 4. 保存order到request域中，转发到/jsps/order/desc.jsp
		 */
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        Order order = new Order();
        order.setOid(CommonUtils.uuid());//设置订单编号
        order.setOrderdate(new Date());//设置下单时间
        order.setState(1);//1表示未付款

        User user = (User) request.getSession().getAttribute("session_user");
        order.setOwner(user);//设置订单所有者
        order.setTotal(cart.getTotal());//合计

			/*
             * 创建订单条目集合
			 * 
			 * cartItemList --> orderItemList
			 */
        List<OrderItem> orderItemList = new ArrayList<OrderItem>();
        // 循环遍历Cart中的所有CartItem，使用每一个CartItem对象创建OrderItem对象，并添加到集合中
        for (CartItem cartItem : cart.getCartItems()) {
            //创建订单条目
            OrderItem oi = new OrderItem();
            oi.setIid(CommonUtils.uuid());//设置订单条目的id
            oi.setCount(cartItem.getCount());
            oi.setSubtotal(cartItem.getSubtotal());
            oi.setOrder(order);
            oi.setBook(cartItem.getBook());

            orderItemList.add(oi);//吧订单条目添加到集合中
        }
        //包所有条目添加到order订单里
        order.setOrderItemList(orderItemList);

        //清除购物车
        cart.clear();

        //调用orderService的add方法处理order订单
        orderService.add(order);
        //将订单保存到request中并转发到jsps/order/desc.jsp
        request.setAttribute("order", order);
        return "f:/jsps/order/desc.jsp";

    }

    /**
     * 我的订单那
     * /
     * <p/>
     * //    public String myOrders(HttpServletRequest request, HttpServletResponse response)
     * //            throws ServletException, IOException {
     * //
     * //        /*
     * //         * 1. 从session得到当前用户，再获取其uid
     * //		 * 2. 使用uid调用orderService#myOrders(uid)得到该用户的所有订单List<Order>
     * //		 * 3. 把订单列表保存到request域中，转发到/jsps/order/list.jsp
     * //
     */
//
//        User user = (User) request.getSession().getAttribute("session_user");
//        List<Order> orderList = orderService.myOrders(user.getUid());
//        request.setAttribute("orderList", orderList);
//        return "f:/jsp/order/list.jsp";
//    }
    public String myOrders(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*
         * 1. 从session得到当前用户，再获取其uid
		 * 2. 使用uid调用orderService#myOrders(uid)得到该用户的所有订单List<Order>
		 * 3. 把订单列表保存到request域中，转发到/jsps/order/list.jsp
		 */
        User user = (User) request.getSession().getAttribute("session_user");
        List<Order> orderList = orderService.myOrders(user.getUid());
        request.setAttribute("orderList", orderList);
        return "f:/jsps/order/list.jsp";
    }

    /**
     * 付款后加载订单，转发到/jsps/order/desc.jsp
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String load(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String oid = request.getParameter("oid");
        //System.out.println(oid);
        request.setAttribute("order", orderService.load(oid));
        return "f:/jsps/order/desc.jsp";
    }

    /**
     * 确认收货
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String confirm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*
         * 1. 获取oid参数
		 * 2. 调用service方法
		 *   > 如果有异常，保存异常信息，转发到msg.jsp
		 * 3. 保存成功信息，转发到msg.jsp
		 */
        String oid = request.getParameter("oid");
        try {
            orderService.confirm(oid);
            request.setAttribute("msg", "恭喜，交易成功！");
        } catch (OrderException e) {
            request.setAttribute("msg", e.getMessage());
        }

        return "f:/jsps/msg.jsp";
    }


}
