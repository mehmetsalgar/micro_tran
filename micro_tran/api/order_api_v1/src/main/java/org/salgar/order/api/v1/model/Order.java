package org.salgar.order.api.v1.model;

import java.util.List;

import org.salgar.product.api.v1.model.Product;

public class Order {
	private Integer id;
	private List<Product> products;
	private Long commitDate;
	private Integer status;
}
