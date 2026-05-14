package za.co.entelect.devcamp.productshopservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.entelect.devcamp.productshopservice.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}