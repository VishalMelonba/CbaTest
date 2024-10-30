package com.petstore.utilities;

import com.github.javafaker.Faker;
import com.model.Category;
import com.model.Pet;
import com.model.Tag;
import io.restassured.response.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.equalTo;

public class PetUtils {
    private final Faker faker = new Faker();


    public int generateRandomPetId() {
        return faker.number().numberBetween(1, 99999);
    }

    public String generateRandomPetName() {
        return faker.animal().name(); // Generates a random animal name
    }

    // Helper method for response validation
    public void validateResponse(Response response, int expectedId, String expectedName) {
        response.then()
                .statusCode(200)
                .body("id", equalTo(expectedId))
                .body("name", equalTo(expectedName))
                .body("status", equalTo("available"));
    }

    public String createPetRequestBody(int id, String name) {
        Pet pet = new Pet();
        pet.setId(id);
        pet.setCategory(createRandomCategory());
        pet.setName(name);
        String DEFAULT_PHOTO_URL = "hhttp://bit.ly/4ebDw8e";
        pet.setPhotoUrls(new String[]{DEFAULT_PHOTO_URL});
        pet.setTags(new Tag[]{createRandomTag()});
        pet.setStatus("available");

        return convertToJson(pet);
    }

    private Category createRandomCategory() {
        Category category = new Category();
        category.setId(faker.number().numberBetween(1, 9999));
        category.setName(faker.animal().name());
        return category;
    }

    private Tag createRandomTag() {
        Tag tag = new Tag();
        tag.setId(faker.number().numberBetween(1, 999));
        tag.setName(faker.animal().name());
        return tag;
    }

    private String convertToJson(Pet pet) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(pet);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error creating JSON", e);
        }
    }
}
