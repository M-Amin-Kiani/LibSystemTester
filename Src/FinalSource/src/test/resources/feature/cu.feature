Feature: Library Management

#Feature: Book Management   Step1

  Scenario: Adding a new book to the library
    Given a library system with no books
    When the librarian adds a book titled "1984" in the genre "Dystopian"
    Then the system should have a book titled "1984" in the genre "Dystopian"
    And the book should be available for borrowing


#Feature: Borrowing Books   Step2

  Scenario: Borrowing a book that is available
    Given a library system with a book titled "To Kill a Mockingbird" in the genre "Fiction"
    And a registered member with ID "123"
    When the member with ID "123" borrows the book titled "To Kill a Mockingbird"
    Then the system should mark the book titled "To Kill a Mockingbird" as borrowed


#Feature: Membership Management

  Scenario: Registering a new member
    Given a library system with no registered members
    When a user registers with the name "amin kiani" and email "aminkiani82@gmail.com"
    Then the system should have a registered member with the name "amin kiani"
    And the membership should not be expired
