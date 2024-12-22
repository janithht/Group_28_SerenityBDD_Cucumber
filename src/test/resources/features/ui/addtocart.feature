@UITests
Feature: Add to cart functionality

  Scenario: Add a product to the cart and verify
    Given I am viewing the product "Radiant Tee"
    When I select size "M"
    And I select color "Blue"
    And I set the quantity to "2"
    And I add the product to the cart
    Then I should see the success message "You added Radiant Tee to your shopping cart." on the product page
    And the cart should display the product "Radiant Tee"

