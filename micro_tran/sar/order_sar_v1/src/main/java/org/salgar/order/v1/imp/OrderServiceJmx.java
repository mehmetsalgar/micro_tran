package org.salgar.order.v1.imp;

import org.salgar.order.api.v1.OrderService;
import org.salgar.order.api.v1.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedOperationParameters;
import org.springframework.jmx.export.annotation.ManagedResource;

@ManagedResource(objectName = "salgar:name=salgar-order-service-v1,type=org.salgar.order.v1.imp.OrderServiceImp,artifactId=salgar-order-service-v1", description = "Product Service V1", log = true, logFile = "jmx.log")
public class OrderServiceJmx implements OrderService {
	@Autowired
	private OrderService orderService;

	@Override
	@ManagedOperation(description = "Gets a parameter as String and delivers an Order")
    @ManagedOperationParameters({
    	@ManagedOperationParameter(name="productId", description="Id of the order that we want to load.")
    })
	
	public Order giveOrder(Integer id) {
		return orderService.giveOrder(id);
	}

	@Override
	@ManagedOperation(description = "Saves an order object")
    @ManagedOperationParameters({
    	@ManagedOperationParameter(name="order", description="Order that we want to save.")
    })
	public void saveOrder(Order order) {
		orderService.saveOrder(order);
	}

	@Override
	@ManagedOperation(description = "Keep alive test")
    @ManagedOperationParameters()
	public String giveAlive() {
		return orderService.giveAlive();
	}
}
