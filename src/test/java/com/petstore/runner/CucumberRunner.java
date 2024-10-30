package com.petstore.runner;

import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;

@CucumberOptions(
        plugin = { "pretty", "html:target/cucumber-report/cucumber.html", "json:target/cucumber-report/cucumber.json" },
        features = {"src/test/resources/features/"},
        monochrome = true,
        glue = {"com.petstore.stepdefinitions"}
)
public class CucumberRunner extends AbstractTestNGCucumberTests {
}
