package com.miguel.catalogchallenge.service;

import com.miguel.catalogchallenge.domain.aws.MessageDTO;
import com.miguel.catalogchallenge.domain.category.Category;
import com.miguel.catalogchallenge.domain.category.exception.CategoryNotFoundException;
import com.miguel.catalogchallenge.domain.product.Product;
import com.miguel.catalogchallenge.domain.product.ProductDTO;
import com.miguel.catalogchallenge.domain.product.exception.ProductNotFoundException;
import com.miguel.catalogchallenge.repository.ProductRepository;
import com.miguel.catalogchallenge.service.aws.AwsSnsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final CategoryService categoryService;
    private final AwsSnsService snsService;

    public Product create(ProductDTO dto) {
        categoryService.findById(dto.categoryId()).orElseThrow(() -> new CategoryNotFoundException(dto.categoryId()));
        Product product = new Product(dto);
        repository.save(product);
        this.snsService.publish(new MessageDTO(product.toString()));
        return product;
    }

    public List<Product> getAll() {
        return repository.findAll();
    }

    public Product update(String id, ProductDTO dto) {
        Product product = this.repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        String title = dto.title();
        String description = dto.description();
        Integer price = dto.price();
        if (StringUtils.hasText(title)) product.setTitle(title);
        if (StringUtils.hasText(description)) product.setDescription(description);
        if (price != null) product.setPrice(price);

        String categoryId = dto.categoryId();

        if (categoryId != null) {
            categoryService.findById(categoryId)
                    .orElseThrow(() -> new CategoryNotFoundException(categoryId));
            product.setCategory(categoryId);
        }
        repository.save(product);
        this.snsService.publish(new MessageDTO(product.toString()));
        return product;
    }

    public void delete(String id) {
        Product product = this.repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        repository.delete(product);
    }

}
