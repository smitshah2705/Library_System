// This will act as the bridge between frontend and backend
package com.librarysystem.library_system;

import java.util.List; // This is a return type for a return multiple objects
import java.util.Optional; // This is a return type for returning a single value or null. Used for cases when a book is not found

import org.springframework.beans.factory.annotation.Autowired; // Used to inject dependiecis so in this case it brings in book repository
//Handles HTTP requests
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // Marks this as a controller handling HTTP requests.
@RequestMapping("/books") // the base path for the URL of all the operations related to “books.”
public class BookController {
    
    @Autowired
    private BookRepository bookRepository; // Input the CRUD operations that we can now use 
    
    // Get all books
    @GetMapping // For get requests
    public List<Book> getAllBooks()
    {
        return bookRepository.findAll();
    }

    //Get a book by ID
    @GetMapping("/{id}") 
    public Optional<Book> getBookById(@PathVariable int id) { // tells Spring to use the value from the URL 
        return bookRepository.findById(id); 
    }

    //Get books by title
    @GetMapping("/title/{title}")
    public List<Book> getBooksByTitle(@PathVariable String title) {
        return bookRepository.findByTitle(title);
    }

     // Search books by author
     @GetMapping("/author/{author}")
     public List<Book> getBooksByAuthor(@PathVariable String author) {
         return bookRepository.findByAuthor(author);
     }

     // Search books by availability
    @GetMapping("/available/{isAvailable}")
    public List<Book> getBooksByAvailability(@PathVariable boolean isAvailable) {
        return bookRepository.findByIsAvailable(isAvailable);
    }

    // Add a new book
    @PostMapping // Used when submitting new data
    public Book addBook(@RequestBody Book book) {
        return bookRepository.save(book); // Saves the new book to the database
    }

    // Update book availability (borrow or return)
    @PutMapping("/{id}/availability") // Handles PUT requests for updating book availability.
    public Book updateBookAvailability(@PathVariable int id, @RequestBody boolean isAvailable) { 
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found")); // If the book is not found, it throws a RuntimeException with the message "Book not found".
        book.setIsAvailable(isAvailable); // Updates availability status.
        return bookRepository.save(book); // Saves the updated book in the database.
    }


    // Delete a book
    @DeleteMapping("/{id}") // For delete requests
    public void deleteBook(@PathVariable int id) {
        bookRepository.deleteById(id); // Deletes the book by ID
    }

}
