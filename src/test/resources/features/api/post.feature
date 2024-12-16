Feature: Book creation

  Scenario: Successfully create a book
    Given User is authorized to create a book as an admin
    When User creates a book with title "New Book Title" and author "New Book Author"
    Then application should return a status code 201 and the created book details
