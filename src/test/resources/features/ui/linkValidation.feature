@UITests
Feature: Broken link detection

  @RequiresLogin
  Scenario: Validate all navigation links for sales Page
    Given I am on the Magento "sale" page
    When I click on each navigation link
    Then I should be redirected to the correct page
    And I should not see a "404 Not Found" error
    And All <a> tags should have a valid href attribute