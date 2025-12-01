package com.medic.inventory.config;

import com.medic.inventory.entity.*;
import com.medic.inventory.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;

    @Override
    public void run(String... args) {
        initializeUsers();
        initializeCategories();
        initializeSuppliers();
        initializeProducts();
    }

    private void initializeUsers() {
        if (userRepository.count() == 0) {
            User admin = new User();
            admin.setEmail("admin@medic.com");
            admin.setPassword("admin123"); // En producción usar BCrypt
            admin.setRole("Administrador");
            admin.setFullName("Administrador Sistema");
            userRepository.save(admin);

            User auxiliar = new User();
            auxiliar.setEmail("auxiliar@medic.com");
            auxiliar.setPassword("auxiliar123");
            auxiliar.setRole("Auxiliar de Almacén");
            auxiliar.setFullName("Juan Pérez");
            userRepository.save(auxiliar);

            System.out.println("✓ Usuarios inicializados");
        }
    }

    private void initializeCategories() {
        if (categoryRepository.count() == 0) {
            String[] categories = {"Analgésicos", "Antibióticos", "Antiinflamatorios", "Vitaminas", "Inhaladores"};

            for (String catName : categories) {
                Category category = new Category();
                category.setNombre(catName);
                categoryRepository.save(category);
            }

            System.out.println("✓ Categorías inicializadas");
        }
    }

    private void initializeSuppliers() {
        if (supplierRepository.count() == 0) {
            Supplier supplier1 = new Supplier();
            supplier1.setName("Ronald Martin");
            supplier1.setContactNumber("987 898 677");
            supplier1.setEmail("ronald@proveedores.com");
            supplier1.setAddress("Av. Principal 123");
            supplier1.setIsActive(true);
            supplierRepository.save(supplier1);

            Supplier supplier2 = new Supplier();
            supplier2.setName("María González");
            supplier2.setContactNumber("956 789 123");
            supplier2.setEmail("maria@proveedores.com");
            supplier2.setAddress("Jr. Comercio 456");
            supplier2.setIsActive(true);
            supplierRepository.save(supplier2);

            Supplier supplier3 = new Supplier();
            supplier3.setName("FarmaPlus SAC");
            supplier3.setContactNumber("945 123 456");
            supplier3.setEmail("ventas@farmaplus.com");
            supplier3.setAddress("Av. Farmacia 789");
            supplier3.setIsActive(true);
            supplierRepository.save(supplier3);

            System.out.println("✓ Proveedores inicializados");
        }
    }

    private void initializeProducts() {
        if (productRepository.count() == 0) {
            Category analgesicos = categoryRepository.findByNombre("Analgésicos").orElse(null);
            Category antibioticos = categoryRepository.findByNombre("Antibióticos").orElse(null);
            Category inhaladores = categoryRepository.findByNombre("Inhaladores").orElse(null);

            Supplier supplier1 = supplierRepository.findById(1L).orElse(null);

            Product product1 = new Product();
            product1.setName("Paracetamol 500mg");
            product1.setBatchNumber("456567");
            product1.setExpirationDate(LocalDate.of(2025, 10, 13));
            product1.setQuantity(34);
            product1.setInitialStock(50);
            product1.setAlertValue(10);
            product1.setPurchasePrice(new BigDecimal("2.10"));
            product1.setSalePrice(new BigDecimal("3.50"));
            product1.setCategory(analgesicos);
            product1.setSupplier(supplier1);
            productRepository.save(product1);

            Product product2 = new Product();
            product2.setName("Amoxicilina 500 mg");
            product2.setBatchNumber("123456");
            product2.setExpirationDate(LocalDate.of(2025, 12, 21));
            product2.setQuantity(8); // CRITICAL STOCK! (< alertValue)
            product2.setInitialStock(40);
            product2.setAlertValue(12);
            product2.setPurchasePrice(new BigDecimal("2.10"));
            product2.setSalePrice(new BigDecimal("4.00"));
            product2.setCategory(antibioticos);
            product2.setSupplier(supplier1);
            productRepository.save(product2);

            Product product3 = new Product();
            product3.setName("Salbutamol Inhalador");
            product3.setBatchNumber("789012");
            product3.setExpirationDate(LocalDate.of(2025, 12, 8));
            product3.setQuantity(10);
            product3.setInitialStock(25);
            product3.setAlertValue(6);
            product3.setPurchasePrice(new BigDecimal("5.10"));
            product3.setSalePrice(new BigDecimal("8.00"));
            product3.setCategory(inhaladores);
            product3.setSupplier(supplier1);
            productRepository.save(product3);

            Product product4 = new Product();
            product4.setName("Ibuprofeno 400 mg");
            product4.setBatchNumber("345678");
            product4.setExpirationDate(LocalDate.of(2025, 1, 9));
            product4.setQuantity(3); // CRITICAL STOCK! (< alertValue)
            product4.setInitialStock(30);
            product4.setAlertValue(10);
            product4.setPurchasePrice(new BigDecimal("1.80"));
            product4.setSalePrice(new BigDecimal("3.20"));
            product4.setCategory(analgesicos);
            product4.setSupplier(supplier1);
            productRepository.save(product4);

            System.out.println("✓ Productos inicializados");
        }
    }
}
