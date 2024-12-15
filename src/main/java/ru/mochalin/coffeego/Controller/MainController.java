package ru.mochalin.coffeego.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.mochalin.coffeego.Model.Product;
import ru.mochalin.coffeego.Repository.ProductRepository;

import java.util.List;

@Controller
public class MainController {
    @Autowired
    private ProductRepository productRepository;

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

    @GetMapping("/cart/{id}")
    public String cart(Model model, @PathVariable Long id) {
		model.addAttribute("products", productRepository.findByCustomers_Id(id));
        return "cart";
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
