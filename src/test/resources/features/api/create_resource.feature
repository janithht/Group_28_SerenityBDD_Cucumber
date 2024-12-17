Feature: Create a new book

    Scenario: Successfully create a new book as an admin
      Given User is authorized as an admin
      When User creates a new book with title <title> and author <author>
      Then application should return status code 201 for create operation
      And the response should include the book ID
      And the book details should match the input title and author
      Examples:
            | title                    | author             |
            | "To Kill a Mockingbird"   | "Harper Lee"       |

    Scenario: Successfully create a new book as a user
      Given User is authorized as a regular user
      When User submits a POST request to create a book with title <title> and author <author>
      Then application should return status code 201 for create operation
      And the response should include the book ID
      And the book details should match the input title and author
      Examples:
            | title       | author          |
            | "1984"      | "George Orwell" |
