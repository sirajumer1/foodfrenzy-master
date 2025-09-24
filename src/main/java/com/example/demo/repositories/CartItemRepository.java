package com.example.demo.repositories;

//package com.foodfrenzy.repository;

import com.example.demo.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    // Find all cart items by cart ID
    List<CartItem> findByCartId(Long cartId);

    // Find cart item by cart ID and product ID
    Optional<CartItem> findByCartIdAndProductId(Long cartId, Long productId);

    // Delete all cart items for a specific cart
    @Modifying
    @Query("DELETE FROM CartItem ci WHERE ci.cart.id = :cartId")
    void deleteByCartId(@Param("cartId") Long cartId);

    // Delete cart item by ID and cart ID
    void deleteByIdAndCartId(Long id, Long cartId);

    // Count items in cart
    long countByCartId(Long cartId);

    // Check if product exists in cart
    boolean existsByCartIdAndProductId(Long cartId, Long productId);
}
