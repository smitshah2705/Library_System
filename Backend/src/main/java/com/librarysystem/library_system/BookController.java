// This will act as the bridge between frontend and backend
package com.librarysystem.library_system;

import java.util.List; 
import java.util.Optional; 

import org.springframework.beans.factory.annotation.Autowired; // Used to inject the BookRepository dependency so that it can be used to interact with the database in the controller
import org.springframework.web.bind.annotation.*;//Handles HTTP requests



@RestController // Marks this as a controller handling HTTP requests.
@RequestMapping("/books") // the base path for the URL of all the operations related to “books.”
public class BookController {
    
    @Autowired
    private BookService bookService; 
    
    @GetMapping // For get requests
    public List<Book> getAllBooks()
    {
        return bookService.getAllBooks();
    }
    @GetMapping("/{id}") 
    public Optional<Book> getBookById(@PathVariable Integer id) { // tells Spring to use the value from the URL 
        return bookService.getBookById(id); 
    }

    @GetMapping("/title/{title}")
    public List<Book> getBooksByTitle(@PathVariable String title) {
        return bookService.getBooksByTitle(title);
    }

     @GetMapping("/author/{author}")
     public List<Book> getBooksByAuthor(@PathVariable String author) {
        return bookService.getBooksByAuthor(author);
     }

    @GetMapping("/available/{isAvailable}")
    public List<Book> getBooksByAvailability(@PathVariable boolean isAvailable) {
        return bookService.getBooksByAvailability(isAvailable);
    }

    @PostMapping // used when you need to send data to the server
    public String addBook(@RequestBody Book book) {
        return bookService.addBook(book); // Saves the new book to the database
    }

    @GetMapping("/borrowedby/{username}")
    public List<Book> getBooksByUser(@PathVariable String username)
    {
        return bookService.getBooksByUser(username);
    }

    @GetMapping("/overdue")
    public List<Book> getBooksByOverDue()
    {
        return bookService.getBooksByOverDue();
    }

    @GetMapping("/overdue/user/{username}")
    public List<Book> getBooksByOverDueForUser(@PathVariable String username)
    {
        return bookService.getBooksByOverDueForUser(username);
    }

    @PutMapping("/checkoverdue/{username}")
    public List<Book> CheckOverdue(@PathVariable String username)
    {
        return bookService.checkOverdue(username);

    }

    @PutMapping("/{id}/borrow") //Used to update the database
    public String borrowBook(@PathVariable Integer id, @RequestParam String studentName) { // Request Param is for Query parameters in the URL such as title=Bookname in the url
        return bookService.borrowBook(id, studentName);
    }

    @PutMapping("/{id}/return")
    public String returnBook(@PathVariable Integer id) {
        return bookService.returnBook(id);
    }

    @DeleteMapping("/{id}") // For delete requests
    public String deleteBook(@PathVariable Integer id) {
        return bookService.deleteBook(id); // Deletes the book by ID
    }

}
