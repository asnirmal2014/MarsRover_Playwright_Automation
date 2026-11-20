@smoke
Feature: Mars Rover Movement

  As a NASA operator
  I want to send commands to a Mars Rover
  So that it can navigate the plateau

  Background:
    Given the Mars Rover web application is open
    And the plateau size is set to "5 5"

  Scenario: Rover moves forward and turns left
    When I set the starting position to "1 2 N"
    And I send the commands "LMLMLMLMM"
    And I click the Execute button
    Then the final position should be "1 3 N"
    