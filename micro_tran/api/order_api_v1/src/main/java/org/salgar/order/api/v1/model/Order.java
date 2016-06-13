package org.salgar.order.api.v1.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.salgar.product.api.v1.model.Product;

@Entity
@Table(name = "ORDER_TA")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "ORDER_PRODUCT_TA", joinColumns = @JoinColumn(name = "ORDERID"), inverseJoinColumns = @JoinColumn(name = "PRODUCTID"))
	private List<Product> products = new ArrayList<Product>();
	private Long commitDate;
	private Integer status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Long getCommitDate() {
		return commitDate;
	}

	public void setCommitDate(Long commitDate) {
		this.commitDate = commitDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
