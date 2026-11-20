@api
Feature: NASA Mars Weather API Testing

  As a QA Engineer
  I want to test the NASA InSight Mars Weather API
  So that I can verify the framework can handle real planetary data

  Scenario: Verify GET request to InSight Mars Weather API
    Given the base URI is set to "https://api.nasa.gov"
    When I send a GET request to "/insight_weather/?api_key=DEMO_KEY&feedtype=json&ver=1.0"
    Then the response status code should be 200
    And the response body should contain "AT"
    