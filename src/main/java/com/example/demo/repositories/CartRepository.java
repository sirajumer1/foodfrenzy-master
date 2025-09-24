package com.example.demo.repositories;

import com.example.demo.entities.Cart;
import com.example.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    // Find cart by user
    //Optional<Cart> findByUser(User user);

    // Find cart by user ID
    Optional<Cart> findByUserId(Long userId);

    // Check if user has a cart
    boolean existsByUserId(Long userId);

    // Delete cart by user ID
    void deleteByUserId(Long userId);

    // Custom query to get cart with items
    @Query("SELECT c FROM Cart c LEFT JOIN FETCH c.cartItems WHERE c.userId = :userId")
    Optional<Cart> findByUserIdWithItems(@Param("userId") Long userId);
}
