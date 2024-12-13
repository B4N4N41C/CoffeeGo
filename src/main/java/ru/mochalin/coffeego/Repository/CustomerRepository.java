package ru.mochalin.coffeego.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.mochalin.coffeego.Model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
