package com.hend.store.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.hend.store.models.Category;
import com.hend.store.services.CategoryRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private static final Logger log = LoggerFactory.getLogger(CategoryController.class);

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping({ "", "/" })
    public String listCategories(Model model) {
        model.addAttribute("categories", categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "name")));
        return "categories/index";
    }

    @GetMapping("/create")
    public String showCreatePage(Model model) {
        model.addAttribute("category", new Category());
        return "categories/CreateCategory";
    }

    @PostMapping("/create")
    public String createCategory(@Valid @ModelAttribute("category") Category category,
                                  BindingResult result) {
        if (result.hasErrors()) {
            return "categories/CreateCategory";
        }
        try {
            categoryRepository.save(category);
        } catch (Exception e) {
            log.error("Error creating category", e);
            result.rejectValue("name", "error.category", "Category name may already exist");
            return "categories/CreateCategory";
        }
        return "redirect:/categories";
    }

    @GetMapping("/edit")
    public String showEditPage(@RequestParam int id, Model model) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null)
            return "redirect:/categories";
        model.addAttribute("category", category);
        return "categories/EditCategory";
    }

    @PostMapping("/edit")
    public String updateCategory(@RequestParam int id,
                                  @Valid @ModelAttribute("category") Category category,
                                  BindingResult result) {
        if (result.hasErrors()) {
            return "categories/EditCategory";
        }
        Category existing = categoryRepository.findById(id).orElse(null);
        if (existing == null)
            return "redirect:/categories";
        existing.setName(category.getName());
        try {
            categoryRepository.save(existing);
        } catch (Exception e) {
            log.error("Error updating category", e);
            result.rejectValue("name", "error.category", "Category name may already exist");
            return "categories/EditCategory";
        }
        return "redirect:/categories";
    }

    @GetMapping("/delete")
    public String deleteCategory(@RequestParam int id) {
        categoryRepository.findById(id).ifPresent(categoryRepository::delete);
        return "redirect:/categories";
    }
}
