package org.salgar.service.rest;

import javax.inject.Named;

import org.salgar.order.api.OrderService;
import org.salgar.order.api.model.Order;
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
	public Order giveOrder(@PathVariable("orderId") Integer orderId) {
		return orderService.giveOrder(orderId);
	}
	
	@RequestMapping(path = "/saveOrder", method = RequestMethod.POST)
	public Order saveOrder(@RequestBody Order order) {
		return orderService.saveOrder(order);
	}
}