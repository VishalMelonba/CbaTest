package com.petstore.utilities;

import com.github.javafaker.Faker;
import com.dto.Category;
import com.dto.Pet;
import com.dto.Tag;
import com.petstore.requestspecification.RequestSpec;
import io.restassured.response.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PetUtils {
    private final Faker faker = new Faker();
    ConfigUtils configUtils = new ConfigUtils();


    public Pet createPet() {
        Pet pet = new Pet();
        pet.setId(generateRandomPetId());
        pet.setCategory(createRandomCategory());
        pet.setName(generateRandomPetName());
        String DEFAULT_PHOTO_URL = "hhttp://bit.ly/4ebDw8e";
        pet.setPhotoUrls(new String[]{DEFAULT_PHOTO_URL});
        pet.setTags(new Tag[]{createRandomTag()});
        pet.setStatus(configUtils.getStatusFromConfig());

        return pet;
    }

    // Send a request to add the pet to the store
    public Response addPetToStore(Pet pet) {
        String requestBody = convertToJson(pet);
        return given().spec(RequestSpec.buildRequest()).body(requestBody).post(configUtils.buildApiUrl(""));
    }

    // Get a pet by ID
    public Response getPetById(long petId) {
        return given().get(configUtils.buildApiUrl("/" + petId));
    }

    // Update pet's details
    public Response updatePet(Pet pet) {
        String requestBody = convertToJson(pet);
        return given().spec(RequestSpec.buildRequest()).body(requestBody).post(configUtils.buildApiUrl(""));
    }

    // Delete a pet by ID
    public Response deletePet(long petId) {
        return given().header("api_key", "special-key")
                .delete(configUtils.buildApiUrl("/" + petId));
    }

    // Helper method for response validation
    public void validateResponse(Response response, Pet pet) {
        response.then()
                .statusCode(200)
                .body("id", equalTo(pet.getId()))
                .body("name", equalTo(pet.getName()))
                .body("status", equalTo("available"));
    }

    public int generateRandomPetId() {
        return faker.number().numberBetween(1, 99999);
    }

    public String generateRandomPetName() {
        return faker.animal().name(); // Generates a random animal name
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
