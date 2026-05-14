package za.co.entelect.devcamp.productshopservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.entelect.devcamp.productshopservice.model.ProductApplication;

@Repository
public interface ProductApplicationRepository extends JpaRepository<ProductApplication, Long> {
}