package com.librarysystem.library_system;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootTest
class BookServiceTest {

    @MockBean
    private BookRepository bookRepository; // Mock the BookRepository

    @InjectMocks
    private BookService bookService; // Inject service into the test

    @Test
    void testGetBooksByUser() {
        // Arrange: Create mock data
        User mockUser = new User("JohnDoe", "password123", "Student");

        // Create Date object for borrowedDate (or use current date)
        Date borrowedDate = new Date(); 

        // Create Book objects using the correct constructor
        Book book1 = new Book("Title 1", "Author 1", true, borrowedDate, mockUser);
        Book book2 = new Book("Title 2", "Author 2", true, borrowedDate, mockUser);

        // Mock the repository to return the books for the user
        when(bookRepository.findByBorrowedby(mockUser)).thenReturn(Arrays.asList(book1, book2));

        // Act: Call the service method
        List<Book> books = bookService.getBooksByUser("JohnDoe");

        // Assert: Verify the result
        assertNotNull(books);
        assertEquals(2, books.size());
        assertEquals("Title 1", books.get(0).getTitle());
        assertEquals("Title 2", books.get(1).getTitle());
    }
}