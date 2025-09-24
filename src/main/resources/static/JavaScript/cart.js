// Configuration
const USER_ID = 1; // Change this to match your logged-in user
const API_BASE_URL = "http://localhost:8080/api";

// Initialize on page load
document.addEventListener('DOMContentLoaded', function() {
    console.log('Cart page loaded');
    loadProducts();
    loadCart();
});

// ============================================
// PRODUCTS FUNCTIONS
// ============================================

async function loadProducts() {
    const productList = document.getElementById('product-list');

    try {
        // Try to fetch from your backend API (if you have a products endpoint)
        const response = await fetch(`${API_BASE_URL}/products`);
        if (response.ok) {
            const products = await response.json();
            displayProducts(products);
        } else {
            throw new Error('Products endpoint not found');
        }
    } catch (error) {
        console.log('Using sample products');
        // Use sample products from your database
        const sampleProducts = [
            { pid: 1, pname: "Afghan Chicken", pprice: 100, pdescription: "Delicious spicy chicken dish" },
            { pid: 2, pname: "Matar Paneer", pprice: 100, pdescription: "Cottage cheese with peas" },
            { pid: 3, pname: "Veg Pulao", pprice: 100, pdescription: "Aromatic rice with vegetables" }
        ];
        displayProducts(sampleProducts);
    }
}

function displayProducts(products) {
    const productList = document.getElementById('product-list');

    if (!products || products.length === 0) {
        productList.innerHTML = '<p class="empty-cart">No products available</p>';
        return;
    }

    productList.innerHTML = '';

    products.forEach(product => {
        const productCard = document.createElement('div');
        productCard.className = 'product-card';
        productCard.innerHTML = `
            <h3>${product.pname || product.name}</h3>
            <p class="price">₹${product.pprice || product.price}</p>
            <p class="description">${product.pdescription || product.description || 'Delicious food item'}</p>
            <button onclick="addToCart(${product.pid || product.id})">
                Add to Cart
            </button>
        `;
        productList.appendChild(productCard);
    });
}

// ============================================
// CART FUNCTIONS
// ============================================

async function loadCart() {
    try {
        const response = await fetch(`${API_BASE_URL}/cart/${USER_ID}`);
        const cart = await response.json();
        displayCart(cart);
    } catch (error) {
        console.error('Error loading cart:', error);
        document.getElementById('cart-items').innerHTML =
            '<p class="empty-cart">Error loading cart. Please refresh the page.</p>';
    }
}

function displayCart(cart) {
    // Update summary
    document.getElementById('total-items').textContent = cart.itemCount || 0;
    document.getElementById('total-price').textContent = (cart.totalPrice || 0).toFixed(2);

    const cartItemsContainer = document.getElementById('cart-items');

    if (!cart.items || cart.items.length === 0) {
        cartItemsContainer.innerHTML = '<p class="empty-cart">Your cart is empty</p>';
        document.getElementById('clear-cart-btn').disabled = true;
        return;
    }

    document.getElementById('clear-cart-btn').disabled = false;
    cartItemsContainer.innerHTML = '';

    cart.items.forEach(item => {
        const cartItem = document.createElement('div');
        cartItem.className = 'cart-item';
        cartItem.innerHTML = `
            <div class="cart-item-header">
                <h4>${item.product.pname || item.product.name}</h4>
                <span class="cart-item-price">₹${item.price}</span>
            </div>
            <div class="cart-item-controls">
                <div class="quantity-control">
                    <button onclick="updateQuantity(${item.id}, ${item.quantity - 1})"
                            ${item.quantity <= 1 ? 'disabled' : ''}>-</button>
                    <span>${item.quantity}</span>
                    <button onclick="updateQuantity(${item.id}, ${item.quantity + 1})">+</button>
                </div>
                <button class="btn-remove" onclick="removeFromCart(${item.id})">Remove</button>
            </div>
            <p style="margin-top: 10px; color: #666;">Subtotal: ₹${(item.price * item.quantity).toFixed(2)}</p>
        `;
        cartItemsContainer.appendChild(cartItem);
    });
}

// ============================================
// API CALLS
// ============================================

async function addToCart(productId) {
    try {
        const response = await fetch(`${API_BASE_URL}/cart/${USER_ID}/add`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                productId: productId,
                quantity: 1
            })
        });

        if (response.ok) {
            showNotification('Product added to cart!', 'success');
            loadCart();
        } else {
            showNotification('Failed to add product', 'error');
        }
    } catch (error) {
        console.error('Error adding to cart:', error);
        showNotification('Error adding to cart', 'error');
    }
}

async function updateQuantity(cartItemId, newQuantity) {
    if (newQuantity < 1) return;

    try {
        const response = await fetch(`${API_BASE_URL}/cart/${USER_ID}/update/${cartItemId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                quantity: newQuantity
            })
        });

        if (response.ok) {
            loadCart();
        } else {
            showNotification('Failed to update quantity', 'error');
        }
    } catch (error) {
        console.error('Error updating quantity:', error);
        showNotification('Error updating quantity', 'error');
    }
}

async function removeFromCart(cartItemId) {
    try {
        const response = await fetch(`${API_BASE_URL}/cart/${USER_ID}/remove/${cartItemId}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            showNotification('Item removed from cart', 'success');
            loadCart();
        } else {
            showNotification('Failed to remove item', 'error');
        }
    } catch (error) {
        console.error('Error removing item:', error);
        showNotification('Error removing item', 'error');
    }
}

async function clearCart() {
    if (!confirm('Are you sure you want to clear your cart?')) {
        return;
    }

    try {
        const response = await fetch(`${API_BASE_URL}/cart/${USER_ID}/clear`, {
            method: 'DELETE'
        });

        if (response.ok) {
            showNotification('Cart cleared successfully', 'success');
            loadCart();
        } else {
            showNotification('Failed to clear cart', 'error');
        }
    } catch (error) {
        console.error('Error clearing cart:', error);
        showNotification('Error clearing cart', 'error');
    }
}

// ============================================
// NOTIFICATION SYSTEM
// ============================================

function showNotification(message, type) {
    // Create notification element
    const notification = document.createElement('div');
    notification.style.cssText = `
        position: fixed;
        top: 20px;
        right: 20px;
        padding: 15px 25px;
        background: ${type === 'success' ? '#28a745' : '#dc3545'};
        color: white;
        border-radius: 8px;
        box-shadow: 0 4px 12px rgba(0,0,0,0.3);
        z-index: 1000;
        animation: slideIn 0.3s ease;
    `;
    notification.textContent = message;

    document.body.appendChild(notification);

    // Remove after 3 seconds
    setTimeout(() => {
        notification.style.animation = 'slideOut 0.3s ease';
        setTimeout(() => notification.remove(), 300);
    }, 3000);
}

// Add CSS animations for notifications
const style = document.createElement('style');
style.textContent = `
    @keyframes slideIn {
        from {
            transform: translateX(400px);
            opacity: 0;
        }
        to {
            transform: translateX(0);
            opacity: 1;
        }
    }
    @keyframes slideOut {
        from {
            transform: translateX(0);
            opacity: 1;
        }
        to {
            transform: translateX(400px);
            opacity: 0;
        }
    }
`;
document.head.appendChild(style);
