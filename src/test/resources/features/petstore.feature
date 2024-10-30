Feature: Pet API

  Scenario: Create a new pet
    Given I have a new pet with details
    When I add the pet to the store
    Then I should see the pet in the store

  Scenario: Find pet by ID
    Given I have added a pet to the store
    When I retrieve the pet by ID
    Then I should see the pet details

  Scenario: Update pet name
    Given I have added a pet to the store
    When I update the pet's name
    Then I should see the updated name when I retrieve the pet

  Scenario: Delete a pet
    Given I have added a pet to the store
    When I delete the pet by ID
    Then the pet should no longer exist in the store
