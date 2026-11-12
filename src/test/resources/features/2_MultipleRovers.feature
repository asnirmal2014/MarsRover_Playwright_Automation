@regression
Feature: Multiple Rovers Navigation

  As a NASA operator
  I want to deploy multiple rovers sequentially
  So that I can explore different areas

  Background:
    Given the Mars Rover web application is open
    And the plateau size is set to "5 5"

  Scenario: Deploy first rover
    When I set the starting position to "1 2 N"
    And I send the commands "LMLMLMLMM"
    And I click the Execute button
    Then the final position should be "1 3 N"

  Scenario: Deploy second rover
    When I set the starting position to "3 3 E"
    And I send the commands "MMRMMRMRRM"
    And I click the Execute button
    Then the final position should be "5 1 E"