package com.example.demo.config;

import com.example.demo.entities.Product;
import com.example.demo.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private com.example.demo.repositories.CartItemRepository cartItemRepository;

    @Override
    public void run(String... args) throws Exception {
        // Clear related data first to avoid foreign key constraints
        System.out.println("Clearing existing cart items...");
        cartItemRepository.deleteAll();

        // Clear existing data to ensure fresh start with images
        System.out.println("Clearing existing product data...");
        productRepository.deleteAll();

        System.out.println("Initializing data with food products...");

        List<Product> products = Arrays.asList(
                // Biryani
                new Product("Hyderabadi Chicken Biryani", 450.0,
                        "Authentic Hyderabadi style chicken biryani with aromatic spices",
                        "/Images/biryani/Biryani_of_Hyderabadi.jpg"),
                new Product("Dum Biryani", 400.0, "Traditional slow-cooked dum biryani",
                        "/Images/biryani/Dum-Biryani.jfif"),
                new Product("Egg Biryani", 320.0, "Flavorful biryani served with boiled eggs",
                        "/Images/biryani/Egg-biryani.jpg"),
                new Product("Lucknowi Biryani", 420.0, "Mildly spiced and aromatic Lucknowi biryani",
                        "/Images/biryani/Lucknowi-biryani.jpg"),
                new Product("Egg Dum Biryani", 340.0, "Dum cooked biryani with eggs",
                        "/Images/biryani/egg-dum-biryani.jpg"),
                new Product("Kolkata Biryani", 380.0, "Kolkata style biryani with potato and egg",
                        "/Images/biryani/kolkata biryani.jpg"),
                new Product("Veg Biryani", 280.0, "Mixed vegetable biryani for vegetarians",
                        "/Images/biryani/veg-biryani.jpg"),
                new Product("Veg Dum Biryani", 300.0, "Slow cooked vegetable dum biryani",
                        "/Images/biryani/veg-dum-biryani.jpeg"),

                // Chicken
                new Product("Afgani Chicken Curry", 380.0, "Rich and creamy Afgani style chicken curry",
                        "/Images/chicken/Afgani-chicken-curry.jpg"),
                new Product("Matka Chicken", 450.0, "Special chicken cooked in clay pot",
                        "/Images/chicken/Matka-Chicken.jpg"),
                new Product("Tandoori Chicken Tikka", 320.0, "Smoky roasted chicken tikka masala",
                        "/Images/chicken/Tandoori-chicken-tikka.jpg"),
                new Product("Butter Chicken", 390.0, "Classic creamy butter chicken",
                        "/Images/chicken/butter-chicken.jpg"),
                new Product("Chicken Karahi", 360.0, "Spicy wok-cooked chicken karahi",
                        "/Images/chicken/chicken-karahi-kadai.jpg"),

                // Chinese
                new Product("Fried Rice & Manchurian", 250.0, "Combo of veg fried rice and manchurian gravy",
                        "/Images/chinese/Fried-rice-manchurian.jpg"),
                new Product("Chilli Paneer", 280.0, "Spicy cottage cheese chunks in indo-chinese sauce",
                        "/Images/chinese/chilli-Paneer.jfif"),
                new Product("Chowmein", 150.0, "Stir-fried noodles with vegetables",
                        "/Images/chinese/chowmein.jpg"),
                new Product("Veg Momos", 120.0, "Steamed dumplings served with spicy chutney",
                        "/Images/chinese/momo.webp"),

                // North Indian
                new Product("Gulab Jamun", 60.0, "Sweet deep-fried dough balls in sugar syrup",
                        "/Images/north-india-food/Gulab Jamun.jpg"),
                new Product("Honey Chilli Potato", 180.0, "Crispy fried potatoes tossed in honey chilli sauce",
                        "/Images/north-india-food/Honey-Chilli-Potato.jpg"),
                new Product("Kheer", 90.0, "Traditional rice pudding dessert", "/Images/north-india-food/Khir.jpg"),
                new Product("Laccha Paratha", 40.0, "Layered flatbread cooked in tandoor",
                        "/Images/north-india-food/Laccha-Paratha.jpg"),
                new Product("Soya Chaap", 220.0, "Delicious soya chaap curry",
                        "/Images/north-india-food/Soya-Chaap.jpg"),
                new Product("Chola Bhatura", 160.0, "Spicy chickpeas served with fried bread",
                        "/Images/north-india-food/chola-bhatura.jpg"),

                // Paneer
                new Product("Kadai Paneer", 300.0, "Spicy paneer cooked with bell peppers",
                        "/Images/paneer/Kadai-Paneer.jpg"),
                new Product("Matar Paneer", 280.0, "Paneer and peas in tomato gravy",
                        "/Images/paneer/Matar-Paneer.jpg"),
                new Product("Paneer Butter Masala", 320.0, "Rich and buttery paneer curry",
                        "/Images/paneer/paneer-butter-masala.jpg"),
                new Product("Paneer Do Pyaza", 310.0, "Paneer preparation with plenty of onions",
                        "/Images/paneer/paneer-do-pyaza.jpg"));

        productRepository.saveAll(products);
        System.out.println("Data initialization completed.");
    }
}
