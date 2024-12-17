Feature: Get all books

  Scenario: Successfully get all books details
    Given User is authorized as an admin to get books
    When the admin user sends a GET request
    Then the status of the response should be 200
    And the response should contain a book with Id <bookId>

      Examples:
          | bookId | title            | author            |
          | 2      | "1984"           | "George Orwell"   |
