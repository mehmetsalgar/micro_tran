package org.salgar.order.v2.dao;

import org.salgar.order.api.v2.model.Order;

public interface OrderRepository {
	Order findOrderByd(Integer orderId);
	Order saveOrder(Order order);
}