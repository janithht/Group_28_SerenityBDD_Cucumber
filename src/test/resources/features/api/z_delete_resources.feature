@APITests
Feature: Delete Book

  Scenario: Attempt to delete a book without providing a book ID
    Given the admin user is logged in
    When the admin sends a DELETE request without specifying a book ID
    Then the server should respond with status code 405

  Scenario: Attempt to delete a non-existent book
    Given the admin user is logged in
    When the admin tries to delete a book with a nonexistent ID 100
    Then the server should respond with status code 404
    And the server should return a "Book not found" message

  Scenario: Admin attempt to delete a book with incorrect credentials
    Given the admin user is logged in with incorrect credentials
    When the admin attempts to delete a book with ID 1 using invalid credentials
    Then the server should respond with status code 401

  Scenario: User without admin privileges attempts to delete a book
    Given a regular user is logged in
    When the regular user tries to delete a book with ID 2
    Then the server should respond with status code 403
    And the server should return a "Forbidden" message

  Scenario: Successfully delete a book as an admin
    Given the admin user is logged in
    And a book with ID 3 exists in the database
    When the admin sends a DELETE request for the book with ID 3
    Then the server should respond with status code 204
    And the database should no longer list the book with ID 3