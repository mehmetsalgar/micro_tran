package org.salgar.order.api;

import java.util.List;

import org.salgar.healthcheck.HealthCheck;
import org.salgar.order.api.model.Order;

public interface OrderService extends HealthCheck {
	Order giveOrder(Integer id);
	Order saveOrder(Order order);
	List<Order> giveCustomerOrders(Integer customerId);
}