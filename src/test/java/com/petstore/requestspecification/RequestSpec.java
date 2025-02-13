package com.petstore.requestspecification;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class RequestSpec {

    // Method to build a RequestSpecification
    public static RequestSpecification buildRequest() {

        return new RequestSpecBuilder()
                .setBaseUri(RestAssured.baseURI)
                .addHeader("Content-Type", "application/json")
                .build();
    }
}
