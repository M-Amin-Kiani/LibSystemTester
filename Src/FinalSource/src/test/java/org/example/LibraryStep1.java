package org.example;

import io.cucumber.java.en.*;
import org.example.Book;
import org.example.LibraryManagementSystemImpl;

import static org.junit.jupiter.api.Assertions.*;

public class LibraryStep1 {

    private LibraryManagementSystemImpl librarySystem;
    private Book addedBook;

    @Given("a library system with no books")
    public void a_library_system_with_no_books() {
        librarySystem = new LibraryManagementSystemImpl();
        assertTrue(librarySystem.getBooksByGenre("Fiction").isEmpty(), "The library should initially have no books.");
    }

    @When("the librarian adds a book titled {string} in the genre {string}")
    public void the_librarian_adds_a_book_titled_in_the_genre(String title, String genre) {
        addedBook = new Book(title, genre);
        librarySystem.addBook(addedBook);
    }

    @Then("the system should have a book titled {string} in the genre {string}")
    public void the_system_should_have_a_book_titled_in_the_genre(String title, String genre) {
        var books = librarySystem.getBooksByGenre(genre);
        assertEquals(1, books.size(), "There should be exactly one book in the genre.");
        assertEquals(title, books.get(0).getTitle(), "The title of the book should match.");
    }

    @Then("the book should be available for borrowing")
    public void the_book_should_be_available_for_borrowing() {
        assertFalse(addedBook.isBorrowed(), "The book should be available for borrowing.");
    }
}
