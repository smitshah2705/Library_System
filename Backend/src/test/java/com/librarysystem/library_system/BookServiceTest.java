import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import com.librarysystem.library_system.Book;
import com.librarysystem.library_system.BookRepository;
import com.librarysystem.library_system.BookService;
import com.librarysystem.library_system.UserRepository;
import com.librarysystem.library_system.User;
import com.librarysystem.library_system.UserService;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private BookService bookService;

    private Map<Integer, Book> mockBookDatabase = new HashMap<>(); // Simulating Book DB
    private Map<Integer, User> mockUserDatabase = new HashMap<>(); // Simulating User DB
    private int bookIdCounter = 1; // Simulating Book ID generation
    private int userIdCounter = 1; // Simulating User ID generation

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockBookDatabase.clear();
        mockUserDatabase.clear();
        bookIdCounter = 1;
        userIdCounter = 1;

        // Mock BookRepository behavior
        when(bookRepository.save(any(Book.class))).thenAnswer(invocation -> {
            Book book = invocation.getArgument(0);
            book.setId(bookIdCounter++); // Simulate auto-incremented ID
            mockBookDatabase.put(book.getId(), book);
            return book;
        });

        when(bookRepository.findById(anyInt())).thenAnswer(invocation -> Optional.ofNullable(mockBookDatabase.get(invocation.getArgument(0))));
        doAnswer(invocation -> {
            mockBookDatabase.remove(invocation.getArgument(0));
            return null;
        }).when(bookRepository).deleteById(anyInt());

        // Mock UserRepository behavior
        when(userRepository.findByUsername(anyString())).thenAnswer(invocation -> {
            String username = invocation.getArgument(0);
            return mockUserDatabase.values().stream()
                    .filter(user -> user.getUsername().equals(username))
                    .findFirst();
        });

        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            mockUserDatabase.put(user.getId(), user);
            return user;
        });
    }

    @Test
    void testBookCreation() {
        Book book = new Book("Book1", "Author1");
        // Manually setting the ID for the test
        book.setId(1); // Set the ID manually for the test

        Book savedBook = bookService.addBook(book);

        assertNotNull(savedBook);
        assertEquals(1, savedBook.getId()); // Simulated ID should be 1
    }

    @Test
    void testBorrowBook() {
        // Setting up a user with Integer ID
        User user = new User("Smit", "321", "Student");
        user.setId(userIdCounter++); // Manually set ID for the test
        mockUserDatabase.put(user.getId(), user); // Add user to mock database

        // Setting up a book
        Book book = new Book("Book1", "Author1");
        book.setId(bookIdCounter++); // Manually set ID for the test
        mockBookDatabase.put(book.getId(), book);

        // Borrowing the book
        String result = bookService.borrowBook(book.getId(), "Smit");

        assertEquals("Book borrowed successfully.", result);
        assertFalse(book.isAvailable());
        assertEquals(user, book.getBorrowedBy());
    }

    @Test
    void testReturnBook() {
        // Setting up a user with Integer ID
        User user = new User("Smit", "474", "Student");
        user.setId(userIdCounter++); // Manually set ID for the test
        mockUserDatabase.put(user.getId(), user); // Add user to mock database

        // Setting up a book
        Book book = new Book("Book1", "Author1");
        book.setId(bookIdCounter++); // Manually set ID for the test
        book.setIsAvailable(false); // Simulate book being borrowed
        book.setBorrowedBy(user); // Set user as the borrower
        book.setBorrowedDate(new Date());
        mockBookDatabase.put(book.getId(), book);

        // Returning the book
        String result = bookService.returnBook(book.getId());

        assertEquals("Book has been returned successfully.", result);
        assertTrue(book.isAvailable());
        assertNull(book.getBorrowedBy());
        assertNull(book.getBorrowedDate());
    }

    @Test
    void testGetBooksByUser(){
        User user = new User("Smit", "109", "Student");
        user.setId(1);

        Book book1 = new Book("Book1", "Author1", false,new Date(), user);
        book1.setId(1);

        Book book2 = new Book("Book2", "Auhtor2", false, new Date(), user);
        book2.setId(2);

        // Mock the userRepository to return the mock user
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        // Mock the bookRepository to return the mock books when querying by borrowedBy
        when(bookRepository.findByBorrowedby(user)).thenReturn(Arrays.asList(book1, book2));

        // Mock bookRepository's findById to return books for borrowBook method
        when(bookRepository.findById(1)).thenReturn(Optional.of(book1));
        when(bookRepository.findById(2)).thenReturn(Optional.of(book2));

        bookService.borrowBook(1, "Smit");
        bookService.borrowBook(2, "Smit");

        List <Book> borrowedBooks = bookService.getBooksByUser("Smit");
        assertNotNull(borrowedBooks);
        assertEquals(2, borrowedBooks.size());
        assertEquals("Book1", borrowedBooks.get(0).getTitle());
        assertEquals("Book2", borrowedBooks.get(1).getTitle());

    }

    @Test
    void testPasswordTesting()
    {
        User user = new User("Smit", "120", "Student");
        user.setId(1);
        mockUserDatabase.put(user.getId(), user);

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        String role = userService.authenticateUser("Smit", "120");

        assertEquals("Student", role); // Check if the role is correctly returned

    }
}