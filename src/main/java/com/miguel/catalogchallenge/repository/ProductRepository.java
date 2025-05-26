package com.miguel.catalogchallenge.repository;

import com.miguel.catalogchallenge.domain.product.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {}