package com.miguel.catalogchallenge.domain.product;

import com.miguel.catalogchallenge.domain.category.Category;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.json.JsonObject;
import org.json.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document("products")
public class Product {
    @Id
    private String id;
    private String title;
    private String description;
    private String ownerId;
    private Integer price;
    private String category;

    public Product(ProductDTO dto) {
        this.title = dto.title();
        this.description = dto.description();
        this.ownerId = dto.ownerId();
        this.price = dto.price();
        this.category = dto.categoryId();
    }

    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("categoryId", category);
        jsonObject.put("title", title);
        jsonObject.put("description", description);
        jsonObject.put("ownerId", ownerId);
        jsonObject.put("price", price);
        jsonObject.put("type", "product");
        return jsonObject.toString();
    }
}
