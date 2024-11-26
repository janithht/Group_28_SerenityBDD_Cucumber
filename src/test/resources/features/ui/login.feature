Feature: Login functionality

  Scenario: Successful login with valid credentials
    Given User is on the login page
    When User login with username and password
    Then User should be logged in and see the account page