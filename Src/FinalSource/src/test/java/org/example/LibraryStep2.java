package org.example;

import io.cucumber.java.en.*;
import org.example.LibraryManagementSystemImpl;
import org.example.Book;
import org.example.Member;
import org.example.ReservationException;

import static org.junit.jupiter.api.Assertions.*;

public class LibraryStep2 {

    private LibraryManagementSystemImpl librarySystem;
    private Book book;
    private Member member;

    @Given("a library system with a book titled {string} in the genre {string}")
    public void a_library_system_with_a_book_titled_in_the_genre(String title, String genre) {
        librarySystem = new LibraryManagementSystemImpl();
        book = new Book(title, genre);
        librarySystem.addBook(book); // کتاب را به کتابخانه اضافه می‌کنیم
    }

    @Given("a registered member with ID {string}")
    public void a_registered_member_with_id(String memberId) {
        member = new Member(Integer.parseInt(memberId), "amin kiani", "aminkiani82@gmail.com");
        librarySystem.registerMember(member); // عضو را ثبت می‌کنیم
    }

    @When("the member with ID {string} borrows the book titled {string}")
    public void the_member_borrows_the_book(String memberId, String title) throws ReservationException {
        // عضو مشخص‌شده کتاب را امانت می‌گیرد
        librarySystem.borrowBook(member, book);
    }

    @Then("the system should mark the book titled {string} as borrowed")
    public void the_system_should_mark_the_book_as_borrowed(String title) {
        assertTrue(book.isBorrowed(), "The book should be marked as borrowed.");
    }

}
