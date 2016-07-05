package org.salgar.order.api.v2;

import org.salgar.healthcheck.HealthCheck;
import org.salgar.order.api.v2.model.Order;

public interface OrderService extends HealthCheck {
	Order giveOrder(Integer orderId);
	Order saveOrder(Order order);
}