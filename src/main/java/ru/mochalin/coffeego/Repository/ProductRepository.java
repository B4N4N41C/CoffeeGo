package ru.mochalin.coffeego.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.mochalin.coffeego.Model.Product;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
        @Query("SELECT p FROM Product p JOIN p.customers c WHERE c.id = ?1")
        List<Product> findByCustomers_Id(Long id);

        List<Product> findByNameContainsIgnoreCase(String name);
}
