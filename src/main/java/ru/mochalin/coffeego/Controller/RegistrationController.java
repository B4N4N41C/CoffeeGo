package ru.mochalin.coffeego.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.mochalin.coffeego.Model.Customer;
import ru.mochalin.coffeego.Model.Role;
import ru.mochalin.coffeego.Repository.CustomerRepository;

@Controller
public class RegistrationController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "register"; // The view name for registration form
    }

   @GetMapping("/login")
	String login() {
		return "login";
	}

    @PostMapping("/register")
    public String registerUser(Customer customer, @RequestParam("password") String password) {
        // Check if the user already exists
        if (customerRepository.existsByEmail(customer.getEmail())) {
            return "redirect:/register?error"; // Redirect to the same page with error
        }

        // Hash the password before saving
        customer.setPassword(passwordEncoder.encode(password));

        // Set the default role (user role)
        customer.setRole(Role.ROLE_USER);

        // Save the customer to the database
        customerRepository.save(customer);

        return "redirect:/login"; // Redirect to login page after registration
    }
}
