package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.entities.Product;
import com.example.demo.repositories.ProductRepository;

@Component
public class ProductServices {
	@Autowired
	private ProductRepository productRepository;

	public void addProduct(Product p) {
		this.productRepository.save(p);
	}

	public List<Product> getAllProducts() {
		List<Product> products = (List<Product>) this.productRepository.findAll();
		return products;
	}

	public Product getProduct(Long id) {
		Optional<Product> optional = this.productRepository.findById(id);
		return optional.orElse(null);
	}

	public void updateproduct(Product p, Long id) {
		p.setPid(id);
		Optional<Product> optional = this.productRepository.findById(id);

		if (optional.isPresent() && optional.get().getPid() == id) {
			this.productRepository.save(p);
		}
	}

	public void deleteProduct(Long id) {
		this.productRepository.deleteById(id);
	}

	public Product getProductByName(String name) {

		Product product = this.productRepository.findByName(name);
		if (product != null) {
			return product;
		}
		return null;

	}
}