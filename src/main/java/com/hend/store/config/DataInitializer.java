package com.hend.store.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.hend.store.models.Category;
import com.hend.store.services.CategoryRepository;

@Component
@Profile("!staging")
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final CategoryRepository categoryRepository;

    public DataInitializer(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) {
        if (categoryRepository.count() > 0) {
            return;
        }
        log.info("Seeding default categories...");
        categoryRepository.save(new Category("Accessories"));
        categoryRepository.save(new Category("Phone"));
        categoryRepository.save(new Category("Computers"));
        categoryRepository.save(new Category("Printers"));
        categoryRepository.save(new Category("Cameras"));
        categoryRepository.save(new Category("Other"));
    }
}
