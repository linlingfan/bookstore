package com.fjnu.bookstore.order.dao;

import cn.itcast.jdbc.TxQueryRunner;
import cn.itcast.utils.CommonUtils;
import com.fjnu.bookstore.book.domain.Book;
import com.fjnu.bookstore.order.domain.Order;
import com.fjnu.bookstore.order.domain.OrderItem;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class OrderDao {

    private QueryRunner qr = new TxQueryRunner();

    /**
     * 添加订单
     *
     * @param order
     */
    public void addOrder(Order order) {
        try {

            String sql = "insert into orders values(?,?,?,?,?,?)";
        /*
         * 处理util的Date转换成sql的Timestamp
		 */
            Timestamp timestamp = new Timestamp(order.getOrderdate().getTime());

            Object[] params = {order.getOid(), timestamp, order.getTotal(), order.getState(),
                    order.getOwner().getUid(), order.getAddress()
            };

            qr.update(sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void addOrderItemList(List<OrderItem> orderItemList) {
        /**
         * QueryRunner类的batch(String sql, Object[][] params)
         * 其中params是多个一维数组！
         * 每个一维数组都与sql在一起执行一次，多个一维数组就执行多次
         */
        try {
            String sql = "insert into orderitem values(?,?,?,?,?)";
        /*
         * 把orderItemList转换成两维数组
		 *   把一个OrderItem对象转换成一个一维数组
		 */
            Object[][] params = new Object[orderItemList.size()][];
            //循环遍历orderItemList，使用每个orderItem对象为params中每个一维数组赋值
            for (int i = 0; i < orderItemList.size(); i++) {
                OrderItem orderItem = orderItemList.get(i);
                params[i] = new Object[]{orderItem.getIid(), orderItem.getCount(),
                        orderItem.getSubtotal(), orderItem.getOrder().getOid(),
                        orderItem.getBook().getBid()};
                //	Object para[i][]={};
            }
            qr.batch(sql, params);//执行批处理
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 按uid查询订单
     */

    public List<Order> findByUid(String uid) {
        /**
         * 1.按uid查询当前用户所有的List<Order>
         * 2.循环遍历每个Order，加载属于当前Order的所有orderItem
         */
        try {
            //得到当前用户的所有订单
            String sql = "select * from orders where uid=?";

            List<Order> orderList = qr.query(sql, new BeanListHandler<Order>(Order.class), uid);

            //循环遍历每个Order为其加载自己所有的订单条目
            for (Order order : orderList) {

                //为order对象添加属于自己的所有订单条条目
                loadOrderItems(order);
            }
            //返回订单列表
            return orderList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 加载指定的订单所有的订单条目
     *
     * @param order
     * @throws SQLException
     */
    private void loadOrderItems(Order order) throws SQLException {
        //查询两张表：orderitem、book
        String sql = "select * from orderitem,book where orderitem.bid=book.bid and oid=?";
        /*
         * 因为一行结果集对应的不再是一个javabean，
		 * 所以不能再使用BeanListHandler，而是MapListHandler
		 */
        List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler(), order.getOid());
        /*
         * mapList是多个map，每个map对应一行结果集(这里面包含了orderitem和book的相关字段)
		 * 我们需要使用一个Map生成两个对象：OrderItem、Book，然后再建立两者的关系
		 * （把Book设置给OrderItem）
		 */
        /*
         * 循环遍历每个Map，使用map生成两个对象，然后建立关系
		 * （最终结果一个OrderItem），把OrderItem保存起来
		 */
        List<OrderItem> orderItemList = toOrderItemList(mapList);
        order.setOrderItemList(orderItemList);

    }

    /**
     * 把mapList中每个Map转换成两个对象，并建立关系
     *
     * @param mapList
     * @return
     */
    private List<OrderItem> toOrderItemList(List<Map<String, Object>> mapList) {
        List<OrderItem> orderItemsList = new ArrayList<OrderItem>();
        for (Map<String, Object> map : mapList) {
            OrderItem items = toOrderItem(map);
            orderItemsList.add(items);
        }
        return orderItemsList;
    }

    /**
     * 把一个Map转换成一个OrderItem对象
     *
     * @param map
     * @return
     */
    private OrderItem toOrderItem(Map<String, Object> map) {
        OrderItem orderItem = CommonUtils.toBean(map, OrderItem.class);
        Book book = CommonUtils.toBean(map, Book.class);
        orderItem.setBook(book);
        return orderItem;

    }

    /**
     * 付款后加载订单
     *
     * @param oid
     * @return
     */
    public Order load(String oid) {
        try {
            String sql = "select * from orders where oid=?";
            Order order = qr.query(sql, new BeanHandler<Order>(Order.class), oid);

            //为此订单加载所有属于他的订单条目
            loadOrderItems(order);
            return order;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 获取订单状态
     *
     * @param oid
     * @return
     */
    public int getOrderState(String oid) {

        try {
            String sql = "select state from orders where oid=?";
            return (Integer) qr.query(sql, new ScalarHandler(), oid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 修改订单的状态
     *
     * @param oid
     * @param state
     */
    public void updateState(String oid, int state) {

        try {
            String sql = "update orders set state=? where oid=?";
            qr.update(sql, state, oid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 后台查看所有订单
     *
     * @return
     */
    public List<Order> findAll() {
        try {
            //得到所有订单
            String sql = "select * from orders order by orderdate";
            List<Order> orderList = qr.query(sql, new BeanListHandler<Order>(Order.class));
            //循环遍历每个Order为其加载自己所有的订单条目
            for (Order order : orderList) {

                //为order对象添加属于自己的所有订单条条目
                loadOrderItems(order);
            }
            //返回订单列表
            return orderList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 通过订单状态查找
     *
     * @param state
     * @return
     */
    public List<Order> findByState(int state) {
        try {
            String sql = "select * from orders where state=? order by orderdate";
            List<Order> orderList = qr.query(sql, new BeanListHandler<Order>(Order.class), state);
            //循环遍历每个Order为其加载自己所有的订单条目
            for (Order order : orderList) {
                //为order对象添加属于自己的所有订单条条目
                loadOrderItems(order);
            }
            //返回订单列表
            return orderList;
        } catch (SQLException e)

        {
            throw new RuntimeException(e);
        }
    }


}