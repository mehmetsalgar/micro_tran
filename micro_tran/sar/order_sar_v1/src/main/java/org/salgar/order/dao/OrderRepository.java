package org.salgar.order.dao;

import java.util.List;

import org.salgar.order.api.model.Order;

public interface OrderRepository {
	Order findById(Integer Id);
	Order saveOrder(Order order);
	List<Order> giveCustomerOrders(Integer customerId);
}
