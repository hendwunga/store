package com.hend.store.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.hend.store.models.Category;
import com.hend.store.models.Product;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ProductsRepositoryTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private ProductsRepository repository;

    private Category category;

    @BeforeEach
    void setUp() {
        category = new Category("Electronics");
        em.persist(category);
        em.flush();
    }

    @Test
    void saveAndFindProduct() {
        Product product = new Product();
        product.setName("Test Phone");
        product.setBrand("TestBrand");
        product.setCategory(category);
        product.setPrice(999.99);
        product.setDescription("A test product description here");
        product.setCreatedAt(LocalDateTime.now());
        product.setImageFileName("http://example.com/img.jpg");

        em.persist(product);
        em.flush();

        List<Product> found = repository.findAll();
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getName()).isEqualTo("Test Phone");
        assertThat(found.get(0).getCategory().getName()).isEqualTo("Electronics");
    }

    @Test
    void searchByNameOrBrand() {
        Product p1 = new Product();
        p1.setName("iPhone 15");
        p1.setBrand("Apple");
        p1.setCategory(category);
        p1.setPrice(1200);
        p1.setDescription("Latest iPhone with great features");
        p1.setCreatedAt(LocalDateTime.now());
        em.persist(p1);

        Product p2 = new Product();
        p2.setName("Galaxy S24");
        p2.setBrand("Samsung");
        p2.setCategory(category);
        p2.setPrice(1100);
        p2.setDescription("Samsung flagship phone");
        p2.setCreatedAt(LocalDateTime.now());
        em.persist(p2);
        em.flush();

        assertThat(repository.findByNameContainingIgnoreCaseOrBrandContainingIgnoreCase("iphone", "iphone")).hasSize(1);
        assertThat(repository.findByNameContainingIgnoreCaseOrBrandContainingIgnoreCase("samsung", "samsung")).hasSize(1);
        assertThat(repository.findByNameContainingIgnoreCaseOrBrandContainingIgnoreCase("apple", "apple")).hasSize(1);
    }
}
