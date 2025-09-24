package com.example.demo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "product_table")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pid")  // Database column is still 'pid'
	private Long id;        // But Java field is 'id' for cart compatibility

	@Column(name = "pname")
	private String name;

	@Column(name = "pprice")
	private Double price;

	@Column(name = "pdescription")
	private String description;

	// Constructors
	public Product() {
	}

	// NEW Getters and Setters (cart-compatible)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	// OPTIONAL: Keep old methods for backward compatibility if needed
	public Long getPid() {
		return id;
	}

	public void setPid(Long pid) {
		this.id = pid;
	}

	public String getPname() {
		return name;
	}

	public void setPname(String pname) {
		this.name = pname;
	}

	public Double getPprice() {
		return price;
	}

	public void setPprice(Double pprice) {
		this.price = pprice;
	}

	public String getPdescription() {
		return description;
	}

	public void setPdescription(String pdescription) {
		this.description = pdescription;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", price=" + price + ", description=" + description + "]";
	}
}
