Feature: Get all books

  Scenario: Successfully get all books details as an admin
    Given User is authorized as an admin to get books
    When the admin user sends a GET all books request
    Then the status of the response should be 200
    And the response should contain a book with Id <bookId>

      Examples:
          | bookId | title            | author            |
          | 2      | "1984"           | "George Orwell"   |

  Scenario: Successfully get all books details as a regular user
    Given User is authorized as a regular user to get books
    When the regular user sends a GET all books request
    Then the status of the response should be 200
    And the response should contain a book with Id <bookId>

        Examples:
            | bookId | title            | author            |
            | 2      | "1984"           | "George Orwell"   |

