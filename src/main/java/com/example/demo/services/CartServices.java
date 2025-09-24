package com.example.demo.services;

import com.example.demo.dto.CartItemRequest;
import com.example.demo.dto.CartResponse;
import com.example.demo.entities.Cart;
import com.example.demo.entities.CartItem;
import com.example.demo.entities.Product;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repositories.CartRepository;
import com.example.demo.repositories.CartItemRepository;
import com.example.demo.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class CartServices {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    /**
     * Add item to cart
     * If item already exists, update quantity
     */
    public CartResponse addToCart(Long userId, CartItemRequest request) {
        // Get or create cart for user
        Cart cart = getOrCreateCart(userId);

        // Find product
        Product product = productRepository.findById(request.getProductId().intValue())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + request.getProductId()));

        // Check if item already exists in cart
        Optional<CartItem> existingItem = cartItemRepository
                .findByCartIdAndProductId(cart.getId(), product.getPid());

        if (existingItem.isPresent()) {
            // Update quantity if item exists
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + request.getQuantity());
            cartItemRepository.save(item);
        } else {
            // Create new cart item
            CartItem newItem = new CartItem(cart, product, request.getQuantity());
            cart.addItem(newItem);
            cartItemRepository.save(newItem);
        }

        // Update cart total
        cart.updateTotalPrice();
        cartRepository.save(cart);

        return convertToCartResponse(cart);
    }

    /**
     * Remove item from cart
     */
    public CartResponse removeFromCart(Long userId, Long cartItemId) {
        Cart cart = getCartByUserId(userId);

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found with id: " + cartItemId));

        // Verify item belongs to user's cart
        if (!cartItem.getCart().getId().equals(cart.getId())) {
            throw new IllegalArgumentException("Cart item does not belong to this cart");
        }

        cart.removeItem(cartItem);
        cartItemRepository.delete(cartItem);

        cart.updateTotalPrice();
        cartRepository.save(cart);

        return convertToCartResponse(cart);
    }

    /**
     * Update item quantity
     */
    public CartResponse updateQuantity(Long userId, Long cartItemId, Integer newQuantity) {
        if (newQuantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        Cart cart = getCartByUserId(userId);

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found with id: " + cartItemId));

        // Verify item belongs to user's cart
        if (!cartItem.getCart().getId().equals(cart.getId())) {
            throw new IllegalArgumentException("Cart item does not belong to this cart");
        }

        cartItem.setQuantity(newQuantity);
        cartItemRepository.save(cartItem);

        cart.updateTotalPrice();
        cartRepository.save(cart);

        return convertToCartResponse(cart);
    }

    /**
     * Get user's cart
     */
    public CartResponse getCart(Long userId) {
        Cart cart = getOrCreateCart(userId);
        return convertToCartResponse(cart);
    }

    /**
     * Clear cart (remove all items)
     */
    public void clearCart(Long userId) {
        Cart cart = getCartByUserId(userId);

        cartItemRepository.deleteByCartId(cart.getId());
        cart.getCartItems().clear();
        cart.updateTotalPrice();
        cartRepository.save(cart);
    }

    /**
     * Get cart item count
     */
    public long getCartItemCount(Long userId) {
        Cart cart = getOrCreateCart(userId);
        return cartItemRepository.countByCartId(cart.getId());
    }

    // Helper methods

    private Cart getOrCreateCart(Long userId) {
        return cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = new Cart(userId);
                    return cartRepository.save(newCart);
                });
    }

    private Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserIdWithItems(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user id: " + userId));
    }

    private CartResponse convertToCartResponse(Cart cart) {
        CartResponse response = new CartResponse();
        response.setCartId(cart.getId());
        response.setUserId(cart.getUserId());
        response.setTotalPrice(cart.getTotalPrice());
        response.setItemCount(cart.getCartItems().size());
        response.setItems(cart.getCartItems());
        response.setCreatedDate(cart.getCreatedDate());
        response.setUpdatedDate(cart.getUpdatedDate());
        return response;
    }
}
