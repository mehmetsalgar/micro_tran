package org.salgar.order.api;

import org.salgar.healthcheck.HealthCheck;
import org.salgar.order.api.model.Order;

public interface OrderService extends HealthCheck {
	Order giveOrder(Integer orderId);
	Order saveOrder(Order order);
}