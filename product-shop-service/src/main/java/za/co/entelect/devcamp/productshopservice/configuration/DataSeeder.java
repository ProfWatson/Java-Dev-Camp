package za.co.entelect.devcamp.productshopservice.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import za.co.entelect.devcamp.productshopservice.model.Customer;
import za.co.entelect.devcamp.productshopservice.model.Product;
import za.co.entelect.devcamp.productshopservice.model.enums.FulfilmentType;
import za.co.entelect.devcamp.productshopservice.repository.CustomerRepository;
import za.co.entelect.devcamp.productshopservice.repository.ProductApplicationRepository;
import za.co.entelect.devcamp.productshopservice.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final ProductApplicationRepository productApplicationRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) {
        seedProducts();
        seedCustomers();
    }

    private void seedProducts() {

        if (productRepository.count() > 0) {
            log.info("Products already seeded. Skipping product seed.");
            return;
        }

        List<Product> products = List.of(

                createProduct(
                        "Retail Short Term Insurance",
                        "Provides cover for short-term products for individuals - Electronics, Household Items, Jewellery, Cars etc.",
                        "500",
                        FulfilmentType.C
                ),

                createProduct(
                        "Retail Long-Term Insurance",
                        "Provides cover for longer term products for individuals - household insurance, life insurance etc.",
                        "1000",
                        FulfilmentType.C
                ),

                createProduct(
                        "Commercial Short Term Insurance",
                        "Provides cover for short-term products for commercial entities - Printers, Company Cars, Theft, etc.",
                        "5000",
                        FulfilmentType.C
                ),

                createProduct(
                        "Commercial Long-Term Insurance",
                        "Provides cover for longer term commercial products - office insurance, employee benefit insurance, etc.",
                        "10000",
                        FulfilmentType.C
                ),

                createProduct(
                        "Device Contract",
                        "Allows the customer to take out a device on contract - such as a phone, laptop etc.",
                        "850",
                        FulfilmentType.A
                ),

                createProduct(
                        "Short-Term Investment Product",
                        "Provides a way for customers to invest their money over a short period of time - 32 day fixed deposit etc.",
                        "2500",
                        FulfilmentType.B
                ),

                createProduct(
                        "Long-Term Investment Product",
                        "Provides a way for users to invest their money over the long term - Retirement / Annuity Funds, Unit Trusts etc.",
                        "5000",
                        FulfilmentType.B
                ),

                createProduct(
                        "Islamic Investment Product",
                        "Provides a way for Islamic customers to invest their money.",
                        "5000",
                        FulfilmentType.B
                ),

                createProduct(
                        "VIP Investment Product",
                        "Provides an investment product for VIP customers over 150 million net-asset value.",
                        "20000",
                        FulfilmentType.B
                )
        );

        productRepository.saveAll(products);

        log.info("Seeded {} products", products.size());
    }

    private Product createProduct(
            String name,
            String description,
            String price,
            FulfilmentType fulfilmentType
    ) {

        Product product = new Product();

        product.setName(name);
        product.setDescription(description);
        product.setPrice(new BigDecimal(price));
        product.setFulfilmentType(fulfilmentType);
        product.setActive(true);

        return product;
    }

    private void seedCustomers() {
        if (customerRepository.count() > 0) {
            log.info("Customers already seeded. Skipping customer seed.");
            return;
        }

        Customer john = new Customer();
        john.setFirstName("John");
        john.setLastName("Doe");
        john.setEmail("john.doe@email.com");
        john.setIdNumber("9001015009087");
        john.setPhoneNumber("0821234567");

        Customer jane = new Customer();
        jane.setFirstName("Jane");
        jane.setLastName("Smith");
        jane.setEmail("jane.smith@email.com");
        jane.setIdNumber("9205157654082");
        jane.setPhoneNumber("0839876543");

        customerRepository.save(john);
        customerRepository.save(jane);

        log.info("Seeded initial customers");
    }
}