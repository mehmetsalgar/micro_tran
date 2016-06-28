package org.salgar.service.rest;

import java.util.List;

import javax.inject.Named;

import org.salgar.order.api.v1.OrderService;
import org.salgar.order.api.v1.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderServiceRest {
	@Autowired
	@Named("proxyOrderService")
	private OrderService orderService;
	
	@RequestMapping("/order/{orderId}")
	public Order giveOrder(@PathVariable("orderId") Integer id) {
		Order order =  orderService.giveOrder(id);
		
		return order;
	}
	
	@RequestMapping(path = "/save_order", method = RequestMethod.POST)
	public Order saveOrder(@RequestBody Order order) {
		return orderService.saveOrder(order);
	}
	
	@RequestMapping("/customerOrders/{customerId}")
	public List<Order> giveCustomerOrders(@PathVariable("customerId") Integer customerId) {
		List<Order> result =  orderService.giveCustomerOrders(customerId);
		
		return result;
	}
}