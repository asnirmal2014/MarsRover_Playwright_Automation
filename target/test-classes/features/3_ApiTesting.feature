@api
Feature: API Testing

  As a QA Engineer
  I want to test the backend API endpoints
  So that I can verify the logic without the UI

  Scenario: Verify GET request to mock API
    Given the base URI is set to "https://jsonplaceholder.typicode.com"
    When I send a GET request to "/posts/1"
    Then the response status code should be 200
    And the response body should contain "userId"