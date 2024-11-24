package ru.mochalin.coffeego.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.mochalin.coffeego.Model.Ingredient;

@Repository
public interface IngredientRepository extends CrudRepository<Ingredient, Long> {
}
