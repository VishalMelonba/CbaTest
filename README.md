# CBATest

This repository contains a test suite for the Petstore API, implemented as part of the CBA Codility test.

# Petstore API Tests

## Overview

The project tests the Petstore API endpoints, with scenarios derived from [Swagger](https://petstore.swagger.io/#/) documentation. It uses Cucumber for behavior-driven testing, and test logs are generated to track execution details.

### Project Structure

- **`pom.xml`**: Maven configuration file for managing project dependencies.
- **`src/main/java/com/model`**: Contains model classes like `Category`, `Pet`, and `Tag`.
- **`src/test/java/com/petstore`**: Holds test-related classes.
    - **`runner/CucumberRunner.java`**: Entry point for running Cucumber tests.
    - **`stepdefinitions/PetSteps.java`**: Step definitions for Petstore API tests.
    - **`utilities`**: Utility classes for configurations and pet-related operations.
- **`src/test/resources`**: Stores configuration files and Cucumber feature files.
    - **`features/petstore.feature`**: Cucumber feature file describing test scenarios.
- **`TestLogs/test.log`**: Log file containing details of test execution. The `TestLogs/` directory is ignored by Git, so logs must be generated locally.

## Requirements

- Java 17 or higher
- Maven

## Setup Instructions

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/VishalMelonba/CbaTest.git
   
2. **Install Dependencies - Use Maven to install required dependencies:**:
   ```bash
   mvn clean install

3. **Run Test:**:
   ```bash
   mvn test

## View Logs:

After test execution, navigate to the TestLogs/ directory to find test.log
It contains detailed information about the test run.

## Manually trigger tests CI:

1. Go to the GitHub repository.
2. Click on the "Actions" tab at the top.
3. Find "CI" workflow listed there.
4. Click on the workflow name to open it.
5. Look for the "Run workflow" button on the right side of the page.


## Auto trigger tests in CI:

- Any push event to a branch that starts with feature/ or main branch
- will initiate the workflow which includes building the project and running tests.



## View Cucumber Report:

- If run the tests on our machine locally. We can see the report url in the console logs.
- Search 'View your Cucumber Report at:'
- Sample Reports - https://reports.cucumber.io/reports/3b0a8833-c9ed-44ed-8f26-a8b0671d22e4
- Also, we can see the reports in CI(GitHub Actions) console logs.
- Search 'View your Cucumber Report at:'