@APITests
Feature: Delete a book

  Scenario: Successfully delete a book as an admin
    Given User is authorized as an admin
    And a book exists with Id <bookId>
    When User sends a DELETE request for the book with Id <bookId>
    Then the response status should be 200
    And the book with Id <bookId> should no longer exist

    Examples:
      | bookId |
      | 1      |

  Scenario: Delete a non-existent book
    Given User is authorized as an admin
    When User sends a DELETE request for a book with Id <bookId>
    Then the response status should be 404
    And the response message should be "Book not found"

    Examples:
      | bookId |
      | 999    |

  Scenario: Delete a book without proper authentication
    Given User is unauthorized
    When User sends a DELETE request for a book with Id <bookId>
    Then the response status should be 401
    And the response message should state "Authentication required"
