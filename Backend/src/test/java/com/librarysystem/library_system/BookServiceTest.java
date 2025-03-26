import static org.mockito.Mockito.*;

import java.beans.Transient;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.librarysystem.library_system.Book;
import com.librarysystem.library_system.BookService;
import com.librarysystem.library_system.BookRepository;
import com.librarysystem.library_system.User;
import com.librarysystem.library_system.UserRepository;
import java.util.Optional;


@ExtendWith(MockitoExtension.class) // Add this annotation to enable mock initialization
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    public void testBookCreation() {
        Book book = new Book("Test Book", "Test Author");
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        
        Book savedBook = bookService.addBook(book); // Assuming the service saves the book
        
        assertNotNull(savedBook);
        assertEquals("Test Book", savedBook.getTitle());
        assertEquals("Test Author", savedBook.getAuthor());
        assertTrue(savedBook.isAvailable()); // Assuming default isAvailable is true
    }

    @Test
    public void testBorrowBook_Success() {
        // Prepare a mock book and user
        Book book = new Book("Test Book", "Test Author");
    
        User user = new User("testUser", "password", "Student");
    
        // Mock the behavior of the repository methods
        when(bookRepository.findById(1)).thenReturn(Optional.of(book)); // Assume the ID of the book is 1
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
    
        // Call the method being tested
        String result = bookService.borrowBook(1, "testUser");
    
        // Verify the results
        assertEquals("Book borrowed successfully.", result);
        assertFalse(book.isAvailable()); // Book should no longer be available
        assertEquals(user, book.getBorrowedBy()); // The user should be set as the borrower
    }

    
}