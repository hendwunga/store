package com.hend.store.services;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hend.store.models.Product;

public interface ProductsRepository extends JpaRepository<Product, Integer> {

}