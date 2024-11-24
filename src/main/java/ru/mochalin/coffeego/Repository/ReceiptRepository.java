package ru.mochalin.coffeego.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.mochalin.coffeego.Model.Receipt;

@Repository
public interface ReceiptRepository extends CrudRepository<Receipt, Long> {
}
