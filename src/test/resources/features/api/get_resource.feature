@APITests
Feature: Get books

  Scenario: Successfully get all books details as an admin
    Given User is authorized as an admin to get books
    When the admin user sends a GET all books request
    Then the status of the response should be 200
    And the response should contain list of available books

      Examples:
          | bookId  | title                     | author                |
          |   1     | "To Kill a Mockingbird"   | "Harper Lee"          |
          |   2     | "1984"                    | "George Orwell"       |
          |   3     | "The Great Gatsby"        | "F. Scott Fitzgerald" |
          |   4     | "Pride and Prejudice"     | "Jane Austen"         |
          |   5     | "The Catcher in the Rye"  | "J.D. Salinger"       |
          |   6     | "The Hobbit"              | "J.R.R. Tolkien"      |
          |   7     | "The Lord of the Rings"   | null                  |
          |   8     |  null                     | "William Shakespeare" |


  Scenario: Successfully get all books details as a regular user
    Given User is authorized as a regular user to get books
    When the regular user sends a GET all books request
    Then the status of the response should be 200
    And the response should contain list of available books

        Examples:
            | bookId  | title                     | author                |
            |   1     | "To Kill a Mockingbird"   | "Harper Lee"          |
            |   2     | "1984"                    | "George Orwell"       |
            |   3     | "The Great Gatsby"        | "F. Scott Fitzgerald" |
            |   4     | "Pride and Prejudice"     | "Jane Austen"         |
            |   5     | "The Catcher in the Rye"  | "J.D. Salinger"       |
            |   6     | "The Hobbit"              | "J.R.R. Tolkien"      |
            |   7     | "The Lord of the Rings"   | null                  |
            |   8     |  null                     | "William Shakespeare" |

  Scenario: Successfully get a book details as an admin
    Given User is authorized as an admin to get books
    When the admin user sends a GET request with ID <bookId>
    Then the status of the response should be 200
    And the response should contain the details of the book

      Examples:
          | bookId | title            | author            |
          | 2      | "1984"           | "George Orwell"   |


   Scenario:Admin receives a 404 error when requesting details for a non-existent book
      Given User is authorized as an admin to get books
      When the admin user sends a GET request with ID <bookId>
      Then the status of the response should be 404
      And the response should contain an error message "Book not found"

        Examples:
            | bookId | title            | author                    |
            | 100    | "Madol Doova"    | "Martin Wickramasinghe"   |

    Scenario: Bug - failing to get a book details as a regular user
       Given User is authorized as a regular user to get books
       When the regular user sends a GET request with ID <bookId>
       Then the status of the response should be 200
       And the response should contain the details of the book

         Examples:
             | bookId | title            | author            |
             | 2      | "1984"           | "George Orwell"   |