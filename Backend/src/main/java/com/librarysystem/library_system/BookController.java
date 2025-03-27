// This will act as the bridge between frontend and backend
package com.librarysystem.library_system;

import java.util.List; // This is a return type for a return multiple objects
import java.util.Optional; // This is a return type for returning a single value or null. Used for cases when a book is not found

import org.springframework.beans.factory.annotation.Autowired; // Used to inject the BookRepository dependency so that it can be used to interact with the database in the controller
import org.springframework.web.bind.annotation.*;//Handles HTTP requests
import com.librarysystem.library_system.BookService;


@RestController // Marks this as a controller handling HTTP requests.
@RequestMapping("/books") // the base path for the URL of all the operations related to “books.”
public class BookController {
    
    @Autowired
    private BookService bookService; 
    
    // Get all books
    @GetMapping // For get requests
    public List<Book> getAllBooks()
    {
        return bookService.getAllBooks();
    }

    //Get a book by ID
    @GetMapping("/{id}") 
    public Optional<Book> getBookById(@PathVariable Integer id) { // tells Spring to use the value from the URL 
        return bookService.getBookById(id); 
    }

    //Get books by title
    @GetMapping("/title/{title}")
    public List<Book> getBooksByTitle(@PathVariable String title) {
        return bookService.getBooksByTitle(title);
    }

     // Search books by author
     @GetMapping("/author/{author}")
     public List<Book> getBooksByAuthor(@PathVariable String author) {
        return bookService.getBooksByAuthor(author);
     }

     // Search books by availability
    @GetMapping("/available/{isAvailable}")
    public List<Book> getBooksByAvailability(@PathVariable boolean isAvailable) {
        return bookService.getBooksByAvailability(isAvailable);
    }

    // Add a new book
    @PostMapping // used when you need to send data to the server
    public Book addBook(@RequestBody Book book) {
        return bookService.addBook(book); // Saves the new book to the database
    }

    @GetMapping("/borrowedby/{username}")
    public List<Book> getBooksByUser(@PathVariable String username)
    {
        return bookService.getBooksByUser(username);
    }

    //Borrow a book
    @PutMapping("/{id}/borrow") //Used to update the database
    public String borrowBook(@PathVariable Integer id, @RequestParam String studentName) { // Request Param is for Query parameters in the URL such as title=Bookname in the url
        return bookService.borrowBook(id, studentName) ;
    }

    //Return a Book
    @PutMapping("/{id}/return")
    public String returnBook(@PathVariable Integer id) {
        return bookService.returnBook(id);
    }


    // Delete a book
    @DeleteMapping("/{id}") // For delete requests
    public String deleteBook(@PathVariable Integer id) {
        return bookService.deleteBook(id); // Deletes the book by ID
    }

}
