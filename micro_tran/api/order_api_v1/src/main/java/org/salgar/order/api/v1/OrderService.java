package org.salgar.order.api.v1;

import java.util.List;

import org.salgar.healthcheck.HealthCheck;
import org.salgar.order.api.v1.model.Order;

public interface OrderService extends HealthCheck {
	Order giveOrder(Integer id);
	Order saveOrder(Order order);
	List<Order> giveCustomerOrders(Integer customerId);
}