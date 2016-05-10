package com.fjnu.bookstore.cart.domian;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {
    private Map<String, CartItem> map = new LinkedHashMap<String, CartItem>();

    /**
     * 计算合计
     *
     * @return
     */
    public double getTotal() {
        BigDecimal total = new BigDecimal("0");
        for (CartItem cartItem : map.values()) {
            BigDecimal subTotal = new BigDecimal(cartItem.getSubtotal());
            total = total.add(subTotal);
        }
        return total.doubleValue();

    }

    /**
     * 添加条目到车中
     *
     * @param cartItem
     */
    public void add(CartItem cartItem) {
        if (map.containsKey(cartItem.getBook().getBid())) {//判断原来购物车是否有条目
            CartItem _cartItem = map.get(cartItem.getBook().getBid());//返回老条目
            _cartItem.setCount(_cartItem.getCount() + cartItem.getCount());
            //老条目的数量等于新条目数量加上之前的老条目数量
            //放回map中
            map.put(cartItem.getBook().getBid(), _cartItem);
        } else
            map.put(cartItem.getBook().getBid(), cartItem);
    }

    /**
     * 清空条目
     */
    public void clear() {
        map.clear();
    }

    /**
     * 删除所选条目
     */
    public void delete(String bid) {
        map.remove(bid);
    }

    /**
     * 获取所有条目
     */

    public Collection<CartItem> getCartItems() {
        return map.values();

    }
}
