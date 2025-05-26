package com.miguel.catalogchallenge.domain.product.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String id) {
        super("Produto não encontrado com o ID: " + id);
    }
}
