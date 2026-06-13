package com.hend.store.services;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hend.store.models.Product;

public interface ProductsRepository extends JpaRepository<Product, Integer> {

    List<Product> findByNameContainingIgnoreCaseOrBrandContainingIgnoreCase(String name, String brand);
}
