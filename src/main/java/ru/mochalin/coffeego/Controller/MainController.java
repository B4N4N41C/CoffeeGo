package ru.mochalin.coffeego.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.persistence.EntityNotFoundException;
import ru.mochalin.coffeego.Model.Customer;
import ru.mochalin.coffeego.Model.Product;
import ru.mochalin.coffeego.Repository.CustomerRepository;
import ru.mochalin.coffeego.Repository.ProductRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class MainController {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("products", productRepository.findAll());
        return "index";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "admin";
    }

    @GetMapping("/cart")
    public String cart(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Customer> emailOptional = customerRepository.findByEmail(email);
        if (emailOptional.isPresent()) {
            Customer customer = emailOptional.get();
            List<Object[]> results = productRepository.findProductsWithCountByCustomerId(customer.getId());
            List<Product> products = new ArrayList<>();
            Map<Product, Integer> productQuantities = new HashMap<>();

            for (Object[] result : results) {
                Product product = (Product) result[0];
                int count = ((Long) result[1]).intValue();
                productQuantities.put(product, count);
                products.add(product);
            }

            int total = 0;
            for (Product product : products) {
                int quantity = productQuantities.get(product);
                total += product.getPrice() * quantity;
            }

            model.addAttribute("products", products);
            model.addAttribute("productQuantities", productQuantities);
            model.addAttribute("total", total);
            return "cart";
        } else {
            throw new EntityNotFoundException("Пользователь не найден");
        }
    }

    @PostMapping("/find")
    public String findProducts(@ModelAttribute Product product,
                              Model model){
        model.addAttribute("product", new Product());
        if(product.getName().isEmpty())
            return "redirect:/";
        List<Product> products = productRepository.findByNameContainsIgnoreCase(product.getName());
        model.addAttribute("products", products);
        return "index";
    }
}
