package ru.mochalin.coffeego.Repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.mochalin.coffeego.Model.Bill;

@Repository
public interface BillRepository extends CrudRepository<Bill, Long> {
   List<Bill> findByOrderDateBetween(Date startDate, Date endDate);
}
