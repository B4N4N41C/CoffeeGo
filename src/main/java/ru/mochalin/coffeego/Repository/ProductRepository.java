package ru.mochalin.coffeego.Repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.mochalin.coffeego.Model.Product;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
        @Query("SELECT p FROM Product p JOIN FETCH p.customers c WHERE c.id = ?1")
        List<Product> findByCustomers_Id(Long id);
        @Query("SELECT p, COUNT(p) FROM Product p JOIN p.customers c WHERE c.id = ?1 GROUP BY p")
        List<Object[]> findProductsWithCountByCustomerId(Long id);

        List<Product> findByNameContainsIgnoreCase(String name);
        
        @Modifying
        @Transactional
        @Query("DELETE FROM Product p WHERE p.id IN (SELECT p.id FROM Product p JOIN p.customers c WHERE c.id = :customerId)")
        void deleteByCustomerId(@Param("customerId") Long customerId);
}
