package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.entities.Product;
import com.example.demo.repositories.ProductRepository;
@Component
public class ProductServices 
{
	@Autowired
	private ProductRepository productRepository;

	public void addProduct(Product p)
	{
		this.productRepository.save(p);
	}

	public List<Product> getAllProducts()
	{
		List<Product> products=(List<Product>)this.productRepository.findAll();
		return products;
	}

	public Product getProduct(Long id) {
		int productId = id.intValue();  // Convert once
		Optional<Product> optional = this.productRepository.findById(productId);
		return optional.orElse(null);  // Better than .get()
	}

	public void updateproduct(Product p, Long id) {
		p.setPid(id);
		int productId = id.intValue();  // Convert once
		Optional<Product> optional = this.productRepository.findById(productId);

		if(optional.isPresent() && optional.get().getPid() == id) {
			this.productRepository.save(p);
		}
	}

	public void deleteProduct(int id)
	{
		this.productRepository.deleteById(id);
	}

	public Product getProductByName(String name)
	{
		
		Product product= this.productRepository.findByName(name);
		if(product!=null)
		{
			return product;
		}
		return null;
	
	}
}