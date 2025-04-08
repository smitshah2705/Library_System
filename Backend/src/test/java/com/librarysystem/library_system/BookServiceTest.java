package com.librarysystem.library_system;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;
import java.util.stream.Collectors;

import com.librarysystem.library_system.Book;
import com.librarysystem.library_system.BookRepository;
import com.librarysystem.library_system.BookService;
import com.librarysystem.library_system.UserRepository;
import com.librarysystem.library_system.User;
import com.librarysystem.library_system.UserService;

class BookServiceTest {
    //HELP OF AI
    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
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

        // Mock basic BookRepository behavior to search the mock db
        when(bookRepository.save(any(Book.class))).thenAnswer(invocation -> {
            Book book = invocation.getArgument(0);
            book.setId(bookIdCounter++); 
            mockBookDatabase.put(book.getId(), book);
            return book;
        });

        when(bookRepository.findById(anyInt())).thenAnswer(invocation -> Optional.ofNullable(mockBookDatabase.get(invocation.getArgument(0))));
        doAnswer(invocation -> {
            mockBookDatabase.remove(invocation.getArgument(0));
            return null;
        }).when(bookRepository).deleteById(anyInt());

        // Mock UserRepository behavior to find in user db
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
    // END OF HELP OF AI

    @Test
    void testBookCreation() {
        Book book = new Book("Book1", "Author1");
        // Manually setting the ID for the test
        book.setId(1); // Set the ID manually for the test

        mockBookDatabase.put(1, book);

        String savedBook = bookService.addBook(book);

        assertEquals("Book has been added successfully", savedBook); 
        
        List<Book> books = new ArrayList<>(mockBookDatabase.values());
        assertTrue(books.contains(book));
    }

    @Test
    void TestGettingAllBooks()
    {
        Book book = new Book("Book", "Author");
        book.setId(1);
        Book book1 = new Book("Book1", "Author1");
        book1.setId(2);
        Book book2 = new Book("Book2", "Author2");
        book2.setId(3);

        mockBookDatabase.put(book.getId(), book);
        mockBookDatabase.put(book1.getId(), book1);
        mockBookDatabase.put(book2.getId(), book2);


        List<Book> books = new ArrayList<>(mockBookDatabase.values());

        assertTrue(books.contains(book));
        assertTrue(books.contains(book1));
        assertTrue(books.contains(book2));
    }

    @Test
    void TestGettingBooksbyTitle()
    {
        Book book = new Book("Book", "Author");
        book.setId(1);

        mockBookDatabase.put(book.getId(), book);

        //AI
        List<Book> books = mockBookDatabase.values()
        .stream()
        .filter(b -> b.getTitle().equals("Book"))
        .collect(Collectors.toList());
        // END OF AI

        assertTrue(books.contains(book));
    }

    @Test
    void TestGettingBooksbyAuthor()
    {
        Book book = new Book("Book", "Author");
        book.setId(1);

        mockBookDatabase.put(book.getId(), book);

        List<Book> books = new ArrayList<>(mockBookDatabase.values())
        .stream()
        .filter(b -> b.getAuthor().equals("Author"))
        .collect(Collectors.toList());

        assertTrue(books.contains(book));
    }

    @Test
    void TestGettingBooksbyAvailibility()
    {
        Book book = new Book("Book", "Author");
        book.setId(1);
        mockBookDatabase.put(1, book);

        User user = new User("Smit", "321", "Student");
        user.setId(1); 
        mockUserDatabase.put(1, user); 

        Book falsebook = new Book("book1", "Author1", false, new Date(),user);
        falsebook.setId(2);
        mockBookDatabase.put(2, falsebook);

        List<Book> books = new ArrayList<>(mockBookDatabase.values())
        .stream()
        .filter(b -> b.isAvailable())
        .collect(Collectors.toList());

        assertTrue(books.contains(book));
        assertFalse(books.contains(falsebook));
    }

    @Test
    void testBorrowBook() {
        User user = new User("Smit", "321", "Student");
        user.setId(1); 
        mockUserDatabase.put(1, user);

        Book book = new Book("Book1", "Author1");
        book.setId(1);
        mockBookDatabase.put(1, book);

        String result = bookService.borrowBook(book.getId(), "Smit");

        assertEquals("Book borrowed successfully.", result);
        assertFalse(book.isAvailable());
        assertEquals(user, book.getBorrowedBy());

        List<Book> userBorrowedBooks = user.getBorrowedBooks();
        assertTrue(userBorrowedBooks.contains(book));
    }

    @Test
    void testReturnBook() {
        User user = new User("Smit", "474", "Student");
        user.setId(1);
        mockUserDatabase.put(1,user);

        Book book = new Book("Book1", "Author1", false, new Date(), user);
        book.setBorrowedDate(new Date());
        book.setId(1);
        mockBookDatabase.put(1,book);
        
        String result = bookService.returnBook(1);

        assertEquals("Book has been returned successfully.", result);
        assertTrue(book.isAvailable());
        assertNull(book.getBorrowedBy());
        assertNull(book.getBorrowedDate());

        List<Book> userBorrowedBooks = user.getBorrowedBooks();
        assertFalse(userBorrowedBooks.contains(book));
    }

    @Test
    void testGetBooksByUser(){
        User user = new User("Smit", "109", "Student");
        user.setId(1);
        mockUserDatabase.put(1,user);

        User user2 = new User("Smith", "119", "Student");
        user2.setId(2);
        mockUserDatabase.put(2,user2);

        Book book1 = new Book("Book1", "Author1");
        book1.setId(1);
        mockBookDatabase.put(1,book1);

        Book book2 = new Book("Book2", "Auhtor2");
        book2.setId(2);
        mockBookDatabase.put(2,book2);

        bookService.borrowBook(1, "Smit");
        bookService.borrowBook(2, "Smith");

        List<Book> userbooks = mockBookDatabase.values()
        .stream()
        .filter(b -> b.getBorrowedBy() != null && b.getBorrowedBy().getId() == user.getId())
        .collect(Collectors.toList());

        List<Book> user2books = mockBookDatabase.values()
        .stream()
        .filter(b -> b.getBorrowedBy() != null && b.getBorrowedBy().getId() == user2.getId())
        .collect(Collectors.toList());

        assertNotNull(userbooks);
        assertNotNull(user2books);
        assertTrue(userbooks.contains(book1));
        assertFalse(userbooks.contains(book2));
        assertTrue(user2books.contains(book2));
        assertFalse(user2books.contains(book1));

    }

    @Test
    void testPasswordTesting()
    {
        User user = new User("Smit", "120", "Student");
        user.setId(1);
        mockUserDatabase.put(1, user);

        String wrong = userService.authenticateUser("Smit", "121");

        assertEquals("Incorrect", wrong);

        String correct = userService.authenticateUser("Smit", "120");

        assertEquals("Student", correct);

    }

    @Test
    void testGetBooksByOverDue()
    {
        User user = new User("Smit", "120", "Student");
        user.setId(1);
        User user1 = new User("Smit1", "121", "Student");
        user1.setId(2);
        User user2 = new User("Smit2", "122", "Student");
        user2.setId(3);

        mockUserDatabase.put(user.getId(), user);
        mockUserDatabase.put(user1.getId(), user1);
        mockUserDatabase.put(user2.getId(), user2);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(calendar.MONTH, -2);
        Date newBD = calendar.getTime();
        
        Book book = new Book("Book", "Author", false, new Date(), user);
        book.setBorrowedDate(newBD);
        book.setId(1);
        Book book1 = new Book("Book1", "Author1", false, new Date(), user1);
        book1.setBorrowedDate(newBD);
        book1.setId(2);
        Book book2 = new Book("Book2", "Author2", false, new Date(), user2);
        book2.setBorrowedDate(newBD);
        book2.setId(3);

        user.getBorrowedBooks().add(book);
        user1.getBorrowedBooks().add(book1);
        user2.getBorrowedBooks().add(book2);
        
        mockBookDatabase.put(book.getId(), book);
        mockBookDatabase.put(book1.getId(), book1);
        mockBookDatabase.put(book2.getId(), book2);

        bookService.checkOverdue("Smit");
        bookService.checkOverdue("Smit1");
        bookService.checkOverdue("Smit2");

        List<Book> overdueBooks = new ArrayList<>(mockBookDatabase.values())
        .stream()
        .filter(b -> b.getOverDue()== true)
        .collect(Collectors.toList());

        assertNotNull(overdueBooks);
        assertEquals(3, overdueBooks.size());
        assertTrue(overdueBooks.contains(book));
        assertTrue(overdueBooks.contains(book1));
        assertTrue(overdueBooks.contains(book2));
    }
}