@APITests
Feature: Delete books from the library system



  Scenario: Attempt to delete a non-existent book without proper permissions
    When User tries to delete a non-existent book with ID <bookId>
    Then The response status code for DELETE operation should be 403
    And The response should state that the user is not permitted
    Examples:
      | bookId |
      | 999    |

  Scenario: Successfully delete a book with a valid ID
    Given User is authorized to delete books as an admin
    When User sends a DELETE request for book ID <bookId>
    Then The response status code for DELETE operation should be 200
    And The response should indicate successful deletion for book ID <bookId>
    Examples:
      | bookId |
      | 101    |

  Scenario: Unauthorized user attempts to delete a book
    When User attempts to delete a book without proper authentication
    Then The response status code for DELETE operation should be 401
    And The response should state that authentication is required for delete operation

  Scenario: Attempt to delete a book with invalid credentials
    Given User is authorized to delete books as an admin
    When User sends a DELETE request for book ID <bookId> with invalid credentials
    Then The response status code for DELETE operation should be 403
    And The response should state that the user is not authorized to perform delete operations
    Examples:
      | bookId |
      | 101    |
