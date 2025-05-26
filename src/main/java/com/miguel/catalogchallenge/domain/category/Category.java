package com.miguel.catalogchallenge.domain.category;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document("categories")
public class Category {
    @Id
    private String id;
    private String title;
    private String description;
    private String ownerId;

    public Category(CategoryDTO dto) {
        this.title = dto.title();
        this.description = dto.description();
        this.ownerId = dto.ownerId();
    }

    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("title", title);
        jsonObject.put("description", description);
        jsonObject.put("ownerId", ownerId);
        jsonObject.put("type", "category");
        return jsonObject.toString();
    }
}
