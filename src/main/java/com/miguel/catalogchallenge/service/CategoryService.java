package com.miguel.catalogchallenge.service;

import com.miguel.catalogchallenge.domain.aws.MessageDTO;
import com.miguel.catalogchallenge.domain.category.Category;
import com.miguel.catalogchallenge.domain.category.CategoryDTO;
import com.miguel.catalogchallenge.domain.category.exception.CategoryNotFoundException;
import com.miguel.catalogchallenge.repository.CategoryRepository;
import com.miguel.catalogchallenge.service.aws.AwsSnsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;
    private final AwsSnsService snsService;

    public Category create(CategoryDTO dto) {
        Category category = new Category(dto);
        repository.save(category);
        this.snsService.publish(new MessageDTO(category.toString()));
        return category;
    }

    public List<Category> getAll() {
        return repository.findAll();
    }

    public Category update(String id, CategoryDTO dto) {
        Category category = this.repository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
        String title = dto.title();
        String description = dto.description();
        if (!title.isEmpty()) category.setTitle(title);
        if (!description.isEmpty()) category.setDescription(description);
        repository.save(category);
        this.snsService.publish(new MessageDTO(category.toString()));
        return category;
    }

    public void delete(String id) {
        Category category = this.repository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
        repository.delete(category);
    }

    public Optional<Category> findById(String id) {
        return this.repository.findById(id);
    }
}
