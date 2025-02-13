package com.petstore.utilities;

import io.restassured.RestAssured;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;


public class BaseTest {

    protected ConfigUtils configUtils;
    private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

    @BeforeClass
    public void setUp() {
        configUtils = ConfigUtils.getInstance();
        RestAssured.baseURI = configUtils.getBaseUrl(); // Set the base URL from config file
        logger.info("Running testFilterByMetro with metro {}", RestAssured.baseURI);
    }
}
