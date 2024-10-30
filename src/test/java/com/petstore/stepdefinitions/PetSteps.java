package com.petstore.stepdefinitions;

import com.petstore.utilities.ConfigUtils;
import com.petstore.utilities.PetUtils;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.asserts.SoftAssert;

public class PetSteps {
    private static final Logger logger = LoggerFactory.getLogger(PetSteps.class);

    ConfigUtils configUtils = new ConfigUtils();
    PetUtils petUtils = new PetUtils();
    private SoftAssert softAssert;

    private int petId;
    private String petName;
    private Response response;
    private final String apiUrl = configUtils.getApiBaseUrl() + "/pet";

    @Before
    public void setUp() {
        softAssert = new SoftAssert();
    }

    @Given("I have a new pet with details")
    public void i_have_a_new_pet_with_details() {
        petId = petUtils.generateRandomPetId();
        petName = petUtils.generateRandomPetName();

        logger.info("Creating a new pet with ID: {}", petId);

        String requestBody = petUtils.createPetRequestBody(petId, petName);
        logger.debug("Request Body for adding new pet: {}", requestBody);

        response = RestAssured.given().contentType("application/json").body(requestBody).post(apiUrl);

        logger.info("Response received: {}", response.getBody().asString());

        petUtils.validateResponse(response, petId, petName);
    }

    @When("I add the pet to the store")
    public void i_add_the_pet_to_the_store() {
        logger.info("Adding the pet to the store");
        softAssert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
    }

    @Then("I should see the pet in the store")
    public void i_should_see_the_pet_in_the_store() {
        logger.info("Verifying that the pet is in the store");
        petUtils.validateResponse(response, petId, petName);
    }

    @Given("I have added a pet to the store")
    public void i_have_added_a_pet_to_the_store() {
        i_have_a_new_pet_with_details();
        i_add_the_pet_to_the_store();
    }

    @When("I retrieve the pet by ID")
    public void i_retrieve_the_pet_by_id() {
        logger.info("Retrieving pet by ID: {}", petId);
        response = RestAssured.get(apiUrl + "/" + petId);
    }

    @Then("I should see the pet details")
    public void i_should_see_the_pet_details() {
        logger.info("Verifying pet details for ID: {}", petId);
        petUtils.validateResponse(response, petId, petName);
    }

    @When("I update the pet's name")
    public void i_update_the_pet_s_name() {
        petName = "UpdatedPetName : " + petUtils.generateRandomPetName(); // New name for the pet
        String requestBody = petUtils.createPetRequestBody(petId, petName);

        logger.info("Updating pet name to: {}", petName);
        logger.debug("Request Body for updating pet: {}", requestBody);

        response = RestAssured.given().contentType("application/json").body(requestBody).put(apiUrl);
        petUtils.validateResponse(response, petId, petName);
    }

    @Then("I should see the updated name when I retrieve the pet")
    public void i_should_see_the_updated_name_when_i_retrieve_the_pet() {
        logger.info("Verifying the updated name for pet ID: {}", petId);
        petUtils.validateResponse(response, petId, petName);
    }

    @When("I delete the pet by ID")
    public void i_delete_the_pet_by_id() {
        logger.info("Deleting pet with ID: {}", petId);
        response = RestAssured.given()
                .header("api_key", "special-key")
                .delete(apiUrl + "/" + petId);

        softAssert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
        softAssert.assertEquals(response.jsonPath().getString("message"),
                String.valueOf(petId), "Message mismatch");
    }

    @Then("the pet should no longer exist in the store")
    public void the_pet_should_no_longer_exist_in_the_store() {
        logger.info("Verifying deletion of pet with ID: {}", petId);
        response = RestAssured.get(apiUrl + "/" + petId);
        softAssert.assertEquals(response.getStatusCode(), 404, "Expected status code 404");
        softAssert.assertEquals(response.jsonPath().getString("message"),
                "Pet not found", "Message mismatch");
    }

    @After
    public void tearDown() {
        softAssert.assertAll(); // Assert all soft assertions
    }
}
