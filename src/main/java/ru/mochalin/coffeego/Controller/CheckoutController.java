package ru.mochalin.coffeego.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.persistence.EntityNotFoundException;
import ru.mochalin.coffeego.Model.Bill;
import ru.mochalin.coffeego.Model.Customer;
import ru.mochalin.coffeego.Model.Product;
import ru.mochalin.coffeego.Repository.BillRepository;
import ru.mochalin.coffeego.Repository.CustomerRepository;
import ru.mochalin.coffeego.Repository.ProductRepository;

@Controller
public class CheckoutController {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private JavaMailSender mailSender;

    @PostMapping("/checkout")
    public String checkout() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));

        List<Object[]> results = productRepository.findProductsWithCountByCustomerId(customer.getId());
        List<Product> products = new ArrayList<>();
        double total = 0;

        for (Object[] result : results) {
            Product product = (Product) result[0];
            int quantity = ((Long) result[1]).intValue();
            products.add(product);
            total += product.getPrice() * quantity;
        }

        if (products.isEmpty()) {
            return "redirect:/"; 
        }

        // Create a new bill
        Bill bill = new Bill();
        bill.setСustomer(customer);
        bill.setProducts(products);
        bill.setTotalPrice(total);
        billRepository.save(bill);

        productRepository.deleteByCustomerId(customer.getId());

        sendOrderConfirmationEmail(customer.getEmail(), bill);

        return "redirect:/";
    }

    @Value("${spring.mail.username}")
    private String from;

    private void sendOrderConfirmationEmail(String to, Bill bill) {
        String subject = "Ваш заказ оформлен!";
        String body = "Здравствуйте, " + bill.getСustomer().getName() + "!\n\n"
                + "Ваш заказ на сумму " + bill.getTotalPrice() + " ₽ успешно оформлен.\n"
                + "Дата заказа: " + bill.getOrderDate() + "\n\n"
                + "Спасибо за покупку в нашем магазине!";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }
}

