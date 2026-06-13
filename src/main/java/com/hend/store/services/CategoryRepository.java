package com.hend.store.services;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hend.store.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
