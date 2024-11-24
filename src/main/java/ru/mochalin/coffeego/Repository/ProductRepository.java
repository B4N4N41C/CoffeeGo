package ru.mochalin.coffeego.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.mochalin.coffeego.Model.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
}
