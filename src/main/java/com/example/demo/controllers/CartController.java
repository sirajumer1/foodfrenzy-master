package com.example.demo.controllers;

import com.example.demo.dto.CartItemRequest;
import com.example.demo.dto.CartResponse;
import com.example.demo.dto.UpdateQuantityRequest;  // ← ADD THIS
import com.example.demo.services.CartServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*")

public class CartController {

    @Autowired
    private CartServices cartServices;

    /**
     * GET /api/cart/{userId}
     * Get user's cart with all items
     */


    @GetMapping("/{userId}")
    public ResponseEntity<CartResponse> getCart(@PathVariable Long userId) {
        CartResponse cart = cartServices.getCart(userId);
        return ResponseEntity.ok(cart);
    }

    /**
     * POST /api/cart/{userId}/add
     * Add item to cart
     */
    @PostMapping("/{userId}/add")
    public ResponseEntity<CartResponse> addToCart(
            @PathVariable Long userId,
            @Valid @RequestBody CartItemRequest request) {
        CartResponse cart = cartServices.addToCart(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(cart);
    }

    /**
     * DELETE /api/cart/{userId}/remove/{itemId}
     * Remove item from cart
     */
    @DeleteMapping("/{userId}/remove/{itemId}")
    public ResponseEntity<CartResponse> removeFromCart(
            @PathVariable Long userId,
            @PathVariable Long itemId) {
        CartResponse cart = cartServices.removeFromCart(userId, itemId);
        return ResponseEntity.ok(cart);
    }

    /**
     * PUT /api/cart/{userId}/update/{itemId}
     * Update item quantity
     */
    @PutMapping("/{userId}/update/{itemId}")
    public ResponseEntity<CartResponse> updateQuantity(
            @PathVariable Long userId,
            @PathVariable Long itemId,
            @Valid @RequestBody UpdateQuantityRequest request) {  // ← FIXED
        CartResponse cart = cartServices.updateQuantity(userId, itemId, request.getQuantity());
        return ResponseEntity.ok(cart);
    }

    /**
     * DELETE /api/cart/{userId}/clear
     * Clear all items from cart
     */
    @DeleteMapping("/{userId}/clear")
    public ResponseEntity<Void> clearCart(@PathVariable Long userId) {
        cartServices.clearCart(userId);
        return ResponseEntity.noContent().build();
    }

    /**
     * GET /api/cart/{userId}/count
     * Get cart item count
     */
    @GetMapping("/{userId}/count")
    public ResponseEntity<CartCountResponse> getCartItemCount(@PathVariable Long userId) {
        long count = cartServices.getCartItemCount(userId);
        return ResponseEntity.ok(new CartCountResponse(count));
    }

    // Helper class for cart count response
    public static class CartCountResponse {
        private long count;

        public CartCountResponse(long count) {
            this.count = count;
        }

        public long getCount() {
            return count;
        }

        public void setCount(long count) {
            this.count = count;
        }
    }
}
