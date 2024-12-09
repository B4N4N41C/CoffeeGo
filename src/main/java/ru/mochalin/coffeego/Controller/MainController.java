package ru.mochalin.coffeego.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.mochalin.coffeego.Repository.ProductRepository;

@Controller
public class MainController {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "index";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "admin";
    }

    @GetMapping("/card")
    public String card(Model model) {
        return "card";
    }
}
