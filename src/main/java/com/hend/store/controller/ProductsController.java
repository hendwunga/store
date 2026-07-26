package com.hend.store.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.hend.store.models.Category;
import com.hend.store.models.Product;
import com.hend.store.models.ProductDTO;
import com.hend.store.services.CategoryRepository;
import com.hend.store.services.ImageStorageService;
import com.hend.store.services.ProductsRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/products")
public class ProductsController {

    private static final Logger log = LoggerFactory.getLogger(ProductsController.class);

    private final ProductsRepository productsRepository;
    private final CategoryRepository categoryRepository;
    private final ImageStorageService imageStorageService;

    public ProductsController(ProductsRepository productsRepository,
                              CategoryRepository categoryRepository,
                              ImageStorageService imageStorageService) {
        this.productsRepository = productsRepository;
        this.categoryRepository = categoryRepository;
        this.imageStorageService = imageStorageService;
    }

    @GetMapping({ "", "/" })
    public String showProductList(@RequestParam(required = false) String search, Model model) {
        List<Product> products;
        if (search != null && !search.isBlank()) {
            products = productsRepository
                .findByNameContainingIgnoreCaseOrBrandContainingIgnoreCase(search, search);
        } else {
            products = productsRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        }
        model.addAttribute("products", products);
        model.addAttribute("search", search);
        return "products/index";
    }

    @GetMapping("/create")
    public String showCreatePage(Model model) {
        model.addAttribute("productDTO", new ProductDTO());
        model.addAttribute("categories", categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "name")));
        return "products/CreateProduct";
    }

    @PostMapping("/create")
    public String createProduct(@Valid @ModelAttribute ProductDTO productDTO,
            BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("categories", categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "name")));
            return "products/CreateProduct";
        }

        try {
            Category category = categoryRepository.findById(productDTO.getCategoryId()).orElse(null);

            Product product = new Product();
            product.setName(productDTO.getName());
            product.setBrand(productDTO.getBrand());
            product.setCategory(category);
            product.setPrice(productDTO.getPrice());
            product.setDescription(productDTO.getDescription());
            product.setCreatedAt(LocalDateTime.now());

            MultipartFile imageFile = productDTO.getImageFile();
            if (imageFile != null && !imageFile.isEmpty()) {
                String imageUrl = imageStorageService.upload(imageFile, "store/products");
                product.setImageFileName(imageUrl);
            } else {
                product.setImageFileName(imageStorageService.getPlaceholder());
            }

            productsRepository.save(product);

        } catch (Exception e) {
            log.error("Error creating product", e);
            model.addAttribute("categories", categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "name")));
            return "products/CreateProduct";
        }

        return "redirect:/products";
    }

    @GetMapping("/edit")
    public String showEditPage(@RequestParam int id, Model model) {
        Product product = productsRepository.findById(id).orElse(null);
        if (product == null)
            return "redirect:/products";

        ProductDTO productDTO = new ProductDTO();
        productDTO.setName(product.getName());
        productDTO.setBrand(product.getBrand());
        productDTO.setCategoryId(product.getCategory() != null ? product.getCategory().getId() : 0);
        productDTO.setPrice(product.getPrice());
        productDTO.setDescription(product.getDescription());

        model.addAttribute("product", product);
        model.addAttribute("productDTO", productDTO);
        model.addAttribute("categories", categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "name")));

        return "products/EditProduct";
    }

    @PostMapping("/edit")
    public String updateProduct(@RequestParam int id,
            @Valid @ModelAttribute ProductDTO productDTO,
            BindingResult result, Model model) {
        Product product = productsRepository.findById(id).orElse(null);
        if (product == null)
            return "redirect:/products";

        model.addAttribute("product", product);

        if (result.hasErrors()) {
            model.addAttribute("categories", categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "name")));
            return "products/EditProduct";
        }

        try {
            MultipartFile imageFile = productDTO.getImageFile();
            if (imageFile != null && !imageFile.isEmpty()) {
                imageStorageService.delete(product.getImageFileName());
                String imageUrl = imageStorageService.upload(imageFile, "store/products");
                product.setImageFileName(imageUrl);
            }

            Category category = categoryRepository.findById(productDTO.getCategoryId()).orElse(null);

            product.setName(productDTO.getName());
            product.setBrand(productDTO.getBrand());
            product.setCategory(category);
            product.setPrice(productDTO.getPrice());
            product.setDescription(productDTO.getDescription());

            productsRepository.save(product);

        } catch (Exception e) {
            log.error("Error updating product", e);
            model.addAttribute("categories", categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "name")));
            return "products/EditProduct";
        }

        return "redirect:/products";
    }

    @GetMapping("/delete")
    public String deleteProduct(@RequestParam int id) {
        productsRepository.findById(id).ifPresent(p -> {
            imageStorageService.delete(p.getImageFileName());
            productsRepository.delete(p);
        });
        return "redirect:/products";
    }
}
