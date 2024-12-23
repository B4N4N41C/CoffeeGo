package ru.mochalin.coffeego.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mochalin.coffeego.Model.Customer;
import java.util.Optional;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
   Optional<Customer> findByEmail(String email);
}
