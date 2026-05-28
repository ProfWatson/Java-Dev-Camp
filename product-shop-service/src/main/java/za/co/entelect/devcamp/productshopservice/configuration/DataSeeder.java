package za.co.entelect.devcamp.productshopservice.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import za.co.entelect.devcamp.productshopservice.model.Customer;
import za.co.entelect.devcamp.productshopservice.model.Product;
import za.co.entelect.devcamp.productshopservice.model.enums.AccountType;
import za.co.entelect.devcamp.productshopservice.model.enums.CustomerType;
import za.co.entelect.devcamp.productshopservice.model.enums.FulfilmentType;
import za.co.entelect.devcamp.productshopservice.model.enums.Role;
import za.co.entelect.devcamp.productshopservice.repository.CustomerRepository;
import za.co.entelect.devcamp.productshopservice.repository.ProductApplicationRepository;
import za.co.entelect.devcamp.productshopservice.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final ProductApplicationRepository productApplicationRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

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
                        FulfilmentType.C,
                        Set.of(CustomerType.INDIVIDUAL),
                        Set.of(AccountType.GOLD_CHEQUE, AccountType.PLATINUM_CHEQUE, AccountType.SIGNET_CHEQUE, AccountType.ISLAMIC_CHEQUE)
                ),
                createProduct(
                        "Retail Long-Term Insurance",
                        "Provides cover for longer term products for individuals - household insurance, life insurance etc.",
                        "1000",
                        FulfilmentType.C,
                        Set.of(CustomerType.INDIVIDUAL),
                        Set.of(AccountType.GOLD_CHEQUE, AccountType.PLATINUM_CHEQUE, AccountType.SIGNET_CHEQUE, AccountType.ISLAMIC_CHEQUE)
                ),
                createProduct(
                        "Commercial Short Term Insurance",
                        "Provides cover for short-term products for commercial entities - Printers, Company Cars, Theft, etc.",
                        "5000",
                        FulfilmentType.C,
                        Set.of(CustomerType.SOLE_PROP, CustomerType.NON_PROFIT, CustomerType.CIPC),
                        Set.of(AccountType.SME_CHECKING, AccountType.MEDIUM_ENTERPRISE_CHECKING, AccountType.LARGE_ENTERPRISE_CHECKING)
                ),
                createProduct(
                        "Commercial Long-Term Insurance",
                        "Provides cover for longer term commercial products - office insurance, employee benefit insurance, etc.",
                        "10000",
                        FulfilmentType.C,
                        Set.of(CustomerType.SOLE_PROP, CustomerType.NON_PROFIT, CustomerType.CIPC),
                        Set.of(AccountType.SME_CHECKING, AccountType.MEDIUM_ENTERPRISE_CHECKING, AccountType.LARGE_ENTERPRISE_CHECKING)
                ),
                createProduct(
                        "Device Contract",
                        "Allows the customer to take out a device on contract - such as a phone, laptop etc.",
                        "850",
                        FulfilmentType.A,
                        Set.of(CustomerType.INDIVIDUAL, CustomerType.SOLE_PROP, CustomerType.NON_PROFIT, CustomerType.CIPC),
                        Set.of(AccountType.GOLD_CHEQUE, AccountType.PLATINUM_CHEQUE, AccountType.SIGNET_CHEQUE, AccountType.ISLAMIC_CHEQUE, AccountType.SAVINGS)
                ),
                createProduct(
                        "Short-Term Investment Product",
                        "Provides a way for customers to invest their money over a short period of time - 32 day fixed deposit etc.",
                        "2500",
                        FulfilmentType.B,
                        Set.of(CustomerType.INDIVIDUAL, CustomerType.SOLE_PROP, CustomerType.NON_PROFIT, CustomerType.CIPC),
                        Set.of(AccountType.GOLD_CHEQUE, AccountType.PLATINUM_CHEQUE, AccountType.ISLAMIC_CHEQUE)
                ),
                createProduct(
                        "Long-Term Investment Product",
                        "Provides a way for users to invest their money over the long term - Retirement / Annuity Funds, Unit Trusts etc.",
                        "5000",
                        FulfilmentType.B,
                        Set.of(CustomerType.INDIVIDUAL, CustomerType.SOLE_PROP, CustomerType.NON_PROFIT, CustomerType.CIPC),
                        Set.of(AccountType.GOLD_CHEQUE, AccountType.PLATINUM_CHEQUE, AccountType.ISLAMIC_CHEQUE)
                ),
                createProduct(
                        "Islamic Investment Product",
                        "Provides a way for Islamic customers to invest their money.",
                        "5000",
                        FulfilmentType.B,
                        Set.of(CustomerType.INDIVIDUAL, CustomerType.NON_PROFIT),
                        Set.of(AccountType.ISLAMIC_CHEQUE)
                ),
                createProduct(
                        "VIP Investment Product",
                        "Provides an Investment product for VIP customers over 150 million net-asset value.",
                        "20000",
                        FulfilmentType.B,
                        Set.of(CustomerType.INDIVIDUAL),
                        Set.of(AccountType.SIGNET_CHEQUE)
                )
        );

        productRepository.saveAll(products);

        log.info("Seeded {} products", products.size());
    }

    private Product createProduct(
            String name,
            String description,
            String price,
            FulfilmentType fulfilmentType,
            Set<CustomerType> qualifyingCustomerTypes,
            Set<AccountType> qualifyingAccountTypes
    ) {

        Product product = new Product();

        product.setName(name);
        product.setDescription(description);
        product.setPrice(new BigDecimal(price));
        product.setFulfilmentType(fulfilmentType);
        product.setActive(true);
        product.setQualifyingCustomerTypes(qualifyingCustomerTypes);
        product.setQualifyingAccountTypes(qualifyingAccountTypes);

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
        john.setPassword(passwordEncoder.encode("Password123!"));
        john.setCustomerType(CustomerType.INDIVIDUAL);
        john.setAccountTypes(Set.of(
                AccountType.GOLD_CHEQUE,
                AccountType.SAVINGS
        ));
        john.setRole(Role.ADMIN);

        Customer jane = new Customer();
        jane.setFirstName("Jane");
        jane.setLastName("Smith");
        jane.setEmail("jane.smith@email.com");
        jane.setIdNumber("9205157654082");
        jane.setPhoneNumber("0839876543");
        jane.setPassword(passwordEncoder.encode("Password123!"));
        jane.setCustomerType(CustomerType.NON_PROFIT);
        jane.setAccountTypes(Set.of(AccountType.ISLAMIC_CHEQUE));
        jane.setRole(Role.CUSTOMER);

        customerRepository.save(john);
        customerRepository.save(jane);

        log.info("Seeded initial customers");
    }
}