package ru.mochalin.coffeego.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.mochalin.coffeego.Model.Product;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
//        @Query(value = "SELECT * FROM product_customer WHERE customer_id = ?1", nativeQuery = true)
        @Query("SELECT p FROM Product p JOIN p.customers c WHERE c.id = ?1")
        List<Product> findByCustomers_Id(Long id);
}
