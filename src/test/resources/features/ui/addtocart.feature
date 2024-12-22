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




Scenario: Add a product to the cart with an invalid quantity
    Given I am viewing the product "Radiant Tee"
    When I select size "M"
    And I select color "Blue"
    And I set the quantity to "-1"
    And I add the product to the cart
    Then I should see an error message "Please enter a quantity greater than 0"
    And the cart should not display the product "Radiant Tee"



Scenario: Add multiple products to the cart and verify
  Given I am viewing the product "Radiant Tee"
  When I select size "M"
  And I select color "Blue"
  And I set the quantity to "2"
  And I add the product to the cart
  
  Given I am viewing the product "Circe Hooded Ice Fleece"
  When I select size "L"
  And I select color "Green"
  And I set the quantity to "3"
  And I add the product to the cart
  Then the cart should display "Radiant Tee" and "Circe Hooded Ice Fleece"



Scenario: Add Circe Hooded Ice Fleece to the cart and verify cart summary
  Given I am viewing the product "Circe Hooded Ice Fleece"
  When I select size "L"
  And I select color "Green"
  And I set the quantity to "1"
  And I add the product to the cart
  Then the cart should display the product "Circe Hooded Ice Fleece"
  And the cart should display the correct price "$68.00"
  And the cart should display the correct quantity "1"
  And the cart should show the total price "$68.00"
  And the cart should show "1" Items in Cart
  And the cart should show "Cart Subtotal $68.00"




Scenario: Add a product to the cart without selecting a size
    Given I am viewing the product "Radiant Tee"
    When I do not select size
    And I select color "Blue"
    And I set the quantity to "2"
    And I add the product to the cart
    Then I should see the error message "This is a required field." for the size field
    And the cart should not display the product "Radiant Tee"