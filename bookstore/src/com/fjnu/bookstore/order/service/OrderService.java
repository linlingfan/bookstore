package com.fjnu.bookstore.order.service;

import java.sql.SQLException;
import java.util.List;

import cn.itcast.jdbc.JdbcUtils;

import com.fjnu.bookstore.order.dao.OrderDao;
import com.fjnu.bookstore.order.domain.Order;

public class OrderService {
    private OrderDao orderDao = new OrderDao();

    public void add(Order order) {
        /**
         * 添加订单
         * 需要处理事务
         * @param order
         */
        try {
            JdbcUtils.beginTransaction();
            orderDao.addOrder(order);//插入订单
            orderDao.addOrderItemList(order.getOrderItemList());//插入订单条目
            //提交事务
            JdbcUtils.commitTransaction();
        } catch (SQLException e) {
            //事务回滚
            try {
                JdbcUtils.rollbackTransaction();
            } catch (SQLException e1) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 我的订单
     *
     * @param uid
     * @return
     */
    public List<Order> myOrders(String uid) {
        return orderDao.findByUid(uid);
    }

    /**
     * 加载订单
     *
     * @param oid
     * @return
     */
    public Order load(String oid) {

        return orderDao.load(oid);
    }

    /**
     * 修改订单状态
     * @param oid
     * @throws OrderException
     */
    public void confirm(String oid) throws OrderException {
        //获取订单状态
        int state = orderDao.getOrderState(oid);
        if (state != 3) throw new OrderException("提交的订单有误！警告！！");
        //否则修改订单状态为4确认收货成功
        orderDao.updateState(oid, 4);

    }

    /**
     * 后台查询所有订单
     * @return
     */
    public List<Order> findAll(){
       return orderDao.findAll();
    }

    /**
     * 通过状态查询订单
     * @param state
     * @return
     */
    public List<Order> findByState(int state) {

        return  orderDao.findByState(state);
    }

    /**
     * 后台确认修改订单状态
     * @param oid
     * @throws OrderException
     */
    public void adminConfirm(String oid) throws OrderException {
        //获取订单状态
        int state = orderDao.getOrderState(oid);
        if (state != 2) throw new OrderException("订单发货有误！警告！！");
        //否则修改订单状态为3确认发货，等待客户确认收货
        orderDao.updateState(oid, 3);

    }
}
