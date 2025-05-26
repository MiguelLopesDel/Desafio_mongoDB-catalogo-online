package com.miguel.catalogchallenge.domain.product;

import com.miguel.catalogchallenge.domain.category.Category;

public record ProductDTO(String title, String description, String ownerId, Integer price, String categoryId) {
}
