package org.salgar.process.context;

import org.salgar.customer.api.model.Customer;
import org.salgar.order.api.model.Order;
import org.salgar.product.api.model.Product;

public class OrderContext {
	private Order order;
	private Customer customer;
	private Product product;

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
}