package org.salgar.process.context.v2;

public class OrderContext {
	org.salgar.customer.api.v2.model.Customer customer;
	org.salgar.product.api.v2.model.Product product;
	org.salgar.order.api.v2.model.Order order;

	public org.salgar.customer.api.v2.model.Customer getCustomer() {
		return customer;
	}

	public void setCustomer(org.salgar.customer.api.v2.model.Customer customer) {
		this.customer = customer;
	}

	public org.salgar.product.api.v2.model.Product getProduct() {
		return product;
	}

	public void setProduct(org.salgar.product.api.v2.model.Product product) {
		this.product = product;
	}

	public org.salgar.order.api.v2.model.Order getOrder() {
		return order;
	}

	public void setOrder(org.salgar.order.api.v2.model.Order order) {
		this.order = order;
	}
}