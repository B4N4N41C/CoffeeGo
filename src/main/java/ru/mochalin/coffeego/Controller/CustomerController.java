package ru.mochalin.coffeego.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public String addProduct(Model model, @PathVariable("id") Long id) {
        Optional<Customer> customer = customerRepository.findById(1L);
        if (customer.isPresent()) {
            List<Product> products = customer.get().getProducts();
            products.add(productRepository.findById(id).get());
            customer.get().setProducts(products);
            customerRepository.save(customer.get());
        }
        return "redirect:/";
    }
}
