Feature: Book update

  Scenario: Successfully update a book details
    Given User is authorized as an admin
    And a book exists with Id <bookId>
    When User updates the book with ID <bookId> setting title to <title>, author to <author>
    Then application should return a status code 200
    And the book with Id <bookId> should have updated details

    Examples:
      | bookId | title            | author       |
      | 1      | "New Adventures"   | "Mark Twain"   |