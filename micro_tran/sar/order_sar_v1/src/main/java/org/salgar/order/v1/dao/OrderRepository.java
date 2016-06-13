package org.salgar.order.v1.dao;

import org.salgar.order.api.v1.model.Order;

public interface OrderRepository {
	Order findById(Integer Id);
	void saveOrder(Order order);
}