@APITests
Feature: Book update

  Scenario: Successfully update a book details
    Given User is authorized as an admin
    And a book exists with Id <bookId>
    When User updates the book with ID <bookId> setting title to <title>, author to <author>
    Then the response status should be 200
    And the book with Id <bookId> should have updated details

    Examples:
      | bookId | title            | author       |
      | 1      | "New Adventures"   | "Mark Twain"   |

  Scenario: Update with Invalid Data
    Given User is authorized as an admin
    And a book exists with Id 1
    When User send a PUT request with invalid data including string in a integer field
    Then the response status should be 400

  Scenario: Update Non-existent Book
    Given User is authorized as an admin
    When User send a PUT request with valid update data
    Then the response status should be 404
    And the response should indicate that the book with ID 101 not found

  Scenario: Update Without Required Fields
    Given User is authorized as an admin
    And a book exists with Id 1
    When User send a PUT request missing the author field
    Then the response status should be 400
    And the response should include a message that Mandatory parameters should not be null

  Scenario: Update with Authentication Errors
    Given User is authorized as an admin
    And a book exists with Id 1
    But the user sends a PUT request to update the book with ID 1 without proper authentication credentials
    Then the response status should be 401
