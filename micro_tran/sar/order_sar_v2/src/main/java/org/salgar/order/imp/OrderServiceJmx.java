package org.salgar.order.imp;

import org.salgar.order.api.OrderService;
import org.salgar.order.api.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedOperationParameters;
import org.springframework.jmx.export.annotation.ManagedResource;

@ManagedResource(objectName = "salgar:name=salgar-order-service-v2,type=org.salgar.order.v2.imp.OrderServiceImp,artifactId=salgar-order-service-v2", description = "Order Service V2", log = true, logFile = "jmx.log")
public class OrderServiceJmx implements OrderService {
	@Autowired
	private OrderService orderService;

	@Override
	@ManagedOperation(description = "Gets a parameter as String and delivers an Order")
    @ManagedOperationParameters({
    	@ManagedOperationParameter(name="orderId", description="Id of the order that we want to load.")
    })
	public Order giveOrder(Integer orderId) {
		return orderService.giveOrder(orderId);
	}

	@Override
	@ManagedOperation(description = "Gets a parameter Order object and persist .it")
    @ManagedOperationParameters({
    	@ManagedOperationParameter(name="orderId", description="Order object that we want to persist.")
    })
	public Order saveOrder(Order order) {
		return orderService.saveOrder(order);
	}
	
	@Override
	@ManagedOperation(description = "Keep alive signal")
	public String giveAlive() {
		return orderService.giveAlive();
	}
}