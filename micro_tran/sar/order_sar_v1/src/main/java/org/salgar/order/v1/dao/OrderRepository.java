package org.salgar.order.v1.dao;

import java.util.List;

import org.salgar.order.api.v1.model.Order;

public interface OrderRepository {
	Order findById(Integer Id);
	Order saveOrder(Order order);
	List<Order> giveCustomerOrders(Integer customerId);
}
