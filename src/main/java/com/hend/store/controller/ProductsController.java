package com.hend.store.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.hend.store.models.Product;
import com.hend.store.models.ProductDTO;
import com.hend.store.services.CloudinaryService;
import com.hend.store.services.ProductsRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @GetMapping({ "", "/" })
    public String showProductList(Model model) {
        List<Product> products = productsRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        model.addAttribute("products", products);
        return "products/index";
    }

    @GetMapping("/create")
    public String showCreatePage(Model model) {
        model.addAttribute("productDTO", new ProductDTO());
        return "products/CreateProduct";
    }

    @PostMapping("/create")
    public String createProduct(@Valid @ModelAttribute ProductDTO productDTO,
            BindingResult result, Model model) {

        if (productDTO.getImageFile() == null || productDTO.getImageFile().isEmpty()) {
            result.addError(new FieldError("productDTO", "imageFile", "Image file is required"));
        }

        if (result.hasErrors()) {
            return "products/CreateProduct";
        }

        try {
            // Upload langsung ke Cloudinary dalam folder store/products
            MultipartFile imageFile = productDTO.getImageFile();
            String imageUrl = cloudinaryService.upload(imageFile, "store/products");

            Product product = new Product();
            product.setName(productDTO.getName());
            product.setBrand(productDTO.getBrand());
            product.setCategory(productDTO.getCategory());
            product.setPrice(productDTO.getPrice());
            product.setDescription(productDTO.getDescription());
            product.setCreatedAt(new Date());
            product.setImageFileName(imageUrl); // simpan URL Cloudinary

            productsRepository.save(product);

        } catch (Exception e) {
            System.out.println("Error uploading product: " + e.getMessage());
            result.addError(new FieldError("productDTO", "imageFile", "Failed to upload image"));
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
        productDTO.setCategory(product.getCategory());
        productDTO.setPrice(product.getPrice());
        productDTO.setDescription(product.getDescription());

        model.addAttribute("product", product);
        model.addAttribute("productDTO", productDTO);

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
            return "products/EditProduct";
        }

        try {
            // Jika ada file baru di-upload
            MultipartFile imageFile = productDTO.getImageFile();
            if (imageFile != null && !imageFile.isEmpty()) {
                String imageUrl = cloudinaryService.upload(imageFile, "store/products");
                product.setImageFileName(imageUrl);
            }

            // Update data lain
            product.setName(productDTO.getName());
            product.setBrand(productDTO.getBrand());
            product.setCategory(productDTO.getCategory());
            product.setPrice(productDTO.getPrice());
            product.setDescription(productDTO.getDescription());

            productsRepository.save(product);

        } catch (Exception e) {
            System.out.println("Error updating product: " + e.getMessage());
            result.addError(new FieldError("productDTO", "imageFile", "Failed to upload image"));
            return "products/EditProduct";
        }

        return "redirect:/products";
    }

    @GetMapping("/delete")
    public String deleteProduct(@RequestParam int id) {
        productsRepository.findById(id).ifPresent(productsRepository::delete);
        return "redirect:/products";
    }
}
