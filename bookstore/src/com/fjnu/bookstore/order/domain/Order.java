package com.fjnu.bookstore.order.domain;

import java.util.Date;
import java.util.List;

import com.fjnu.bookstore.user.domain.User;

public class Order {
		private String oid;//订单号
		private Date orderdate;// 订单日期
		private double total;//合计
		private int state;// 订单状态  有四种：1未付款 2已付款但未发货 3已发货但未确认收货 4已确认交易成功
		private User owner;// 用户id
		private String address;// 地址
		private List<OrderItem> orderItemList;//当前订单下的所有订单条目
		
		
		public List<OrderItem> getOrderItemList() {
			return orderItemList;
		}
		public void setOrderItemList(List<OrderItem> orderItemList) {
			this.orderItemList = orderItemList;
		}
		public String getOid() {
			return oid;
		}
		public Date getOrderdate() {
			return orderdate;
		}
		public double getTotal() {
			return total;
		}
		public int getState() {
			return state;
		}
		public User getOwner() {
			return owner;
		}
		public String getAddress() {
			return address;
		}
		public void setOid(String oid) {
			this.oid = oid;
		}
		public void setOrderdate(Date orderdate) {
			this.orderdate = orderdate;
		}
		public void setTotal(double total) {
			this.total = total;
		}
		public void setState(int state) {
			this.state = state;
		}
		public void setOwner(User owner) {
			this.owner = owner;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		

}
