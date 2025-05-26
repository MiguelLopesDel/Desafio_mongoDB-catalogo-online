package com.miguel.catalogchallenge.controller;

import com.miguel.catalogchallenge.domain.category.Category;
import com.miguel.catalogchallenge.domain.category.CategoryDTO;
import com.miguel.catalogchallenge.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<Category> create(@RequestBody CategoryDTO dto) {
        Category category = categoryService.create(dto);
        return ResponseEntity.ok(category);
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAll() {
        return ResponseEntity.ok(categoryService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> update(@PathVariable("id") String id, @RequestBody CategoryDTO dto) {
        Category category = categoryService.update(id, dto);
        return ResponseEntity.ok(category);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id) {
        categoryService.delete(id);
        ResponseEntity.noContent().build();
    }
}
