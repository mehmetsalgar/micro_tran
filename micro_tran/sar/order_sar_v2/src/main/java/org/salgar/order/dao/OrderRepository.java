package org.salgar.order.dao;

import org.salgar.order.api.model.Order;

public interface OrderRepository {
	Order findOrderByd(Integer orderId);
	Order saveOrder(Order order);
}