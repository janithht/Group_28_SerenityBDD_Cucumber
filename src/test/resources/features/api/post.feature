Feature: Create a new book

  Scenario: Successfully create a new book
    Given User is authorized as an admin
    When User creates a new book with title <title> and author <author>
    Then application should return a status code 201
    And the response should include the book ID
    And the book details should match the input title and author

    Examples:
      | title                | author          |
      | "To Kill a Mockingbird" | "Harper Lee"     |

