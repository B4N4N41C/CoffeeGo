package ru.mochalin.coffeego;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {
   @Bean
   SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
      http
      .authorizeHttpRequests((auth) -> auth
              .requestMatchers("/login", "/register", "/logout").permitAll()
              .anyRequest().authenticated())
      .formLogin(form -> form
        .loginPage("/login")
        .defaultSuccessUrl("/", true) 
        .permitAll())
      .logout(logout -> logout
              .logoutUrl("/logout")
              .logoutSuccessUrl("/login")
              .permitAll()
              .invalidateHttpSession(true)
              .deleteCookies("JSESSIONID")
      );
       return http.build();
   }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
