package org.salgar.order.api.v1;

import org.salgar.healthcheck.HealthCheck;
import org.salgar.order.api.v1.model.Order;

public interface OrderService extends HealthCheck {
	Order giveOrder(Integer id);
	void saveOrder(Order order);
}