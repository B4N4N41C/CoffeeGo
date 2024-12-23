package ru.mochalin.coffeego;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ru.mochalin.coffeego.Model.Role;
import ru.mochalin.coffeego.Repository.CustomerRepository;

@Service
public class UserService implements UserDetailsService {
   @Autowired
   CustomerRepository customerRepository;
   @Override
   public UserDetails loadUserByUsername(String email) 
   throws UsernameNotFoundException {
        return customerRepository.findByEmail(email)
                .map(user ->
                        new org.springframework.security.core.userdetails.User(
                                user.getUsername(),
                                user.getPassword(),
                                Collections.singleton(user.getRole() != null ? user.getRole() : Role.ROLE_USER)
                        ))
                .orElseThrow(() -> new
                        UsernameNotFoundException("Невозможно найти пользователя " + email));
    }
}
