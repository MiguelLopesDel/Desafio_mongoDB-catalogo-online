package com.miguel.catalogchallenge.domain.category.exception;

public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException(String id) {
        super("Categoria não encontrada: " + id);
    }
}
