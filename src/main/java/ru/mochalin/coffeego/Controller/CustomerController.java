package ru.mochalin.coffeego.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.persistence.EntityNotFoundException;
import ru.mochalin.coffeego.Model.Customer;
import ru.mochalin.coffeego.Model.Product;
import ru.mochalin.coffeego.Repository.CustomerRepository;
import ru.mochalin.coffeego.Repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/add-product/{id}")
    public String addProduct(@PathVariable("id") Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Customer> customerOptional = customerRepository.findByEmail(email);
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isPresent() && customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            List<Product> products = customer.getProducts();
            products.add(productOpt.get());
            customer.setProducts(products);
            customerRepository.save(customer);
        }
        return "redirect:/";
    }

    @GetMapping("/profile")
    public String showProfile(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));
        model.addAttribute("customer", customer);
        return "profile";
    }
}
