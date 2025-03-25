//Contains logic. uses actions from the repository and conditions to make rules. It will handle the rules and conditions before applying the actions on the data
//The controller can't directly connect with the reposiotry is because the controller's job is to handle HTTPrequest and it woul make the code too complicated ifyou also included business logic
// so to make it mroeclean and easy to manage Services classes are used

package com.librarysystem.library_system;

import org.springframework.stereotype.Service;  // Import the @Service annotation to mark this class as a Spring service - whihc means this code contains logic

import java.util.List; // Used to ouput more than one object
import java.util.Optional; //It holds exactly one value and it doesnt not necessarily contain a value.

import org.springframework.beans.factory.annotation.Autowired; // Used to inject dependency 

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    //Get all books
    public List<Book> getAllBooks()
    {
        return bookRepository.findAll();
    }

    //Get a book by ID
    public Optional<Book> getBookById(int id) { 
        return bookRepository.findById(id); 
    }

    //Get books by title
    public List<Book> getBooksByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

     // Search books by author
     public List<Book> getBooksByAuthor(String author) {
        return bookRepository.findByAuthor(author);
     }

     // Search books by availability
    public List<Book> getBooksByAvailability(boolean isAvailable) {
        return bookRepository.findByIsAvailable(isAvailable);
    }

    // Get all books borrowed by a User
    public List<Book> getBooksByUser(String username){
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        return bookRepository.findByBorrowedby(user);
    }

    public String borrowBook(int id, String Username)
    {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));

        if(!book.isAvailable())
        {
            return "Book is already reserved.";
        }

        //Find the USer by their Id
        User user = userRepository.findByUsername(Username).orElseThrow(() -> new RuntimeException("User not found"));


        book.setIsAvailable(false); // Set the book as unavailable
        book.setBorrowedBy(user); // Set the ID of the student who borrowed the book
        book.setBorrowedDate(new java.util.Date()); // Set the borrow date. creates a new Date object that represents the current date and time 
        user.getBorrowedBooks().add(book); //Add the new book to the user's borrowed list
        
        bookRepository.save(book); // Save the updated book record
        userRepository.save(user); //Save the user updated database
        return "Book borrowed successfully.";

    }

    public String returnBook(int id)
    {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));

        if(book.isAvailable())
        {
            return "Book has already been returned.";
        }
        User user = book.getBorrowedBy();

        user.getBorrowedBooks().remove(book);
        userRepository.save(user);

        book.setIsAvailable(true);
        book.setBorrowedBy(null);
        book.setBorrowedDate(null);
        bookRepository.save(book);
        return "Book has been returned successfully.";

    }


    // Add a new book
    public Book addBook(Book book) {
        return bookRepository.save(book); // Saves the new book to the database
    }

    // Delete a book
    public String deleteBook(int id) {
        if(bookRepository.existsById(id))
        {
            bookRepository.deleteById(id);
            return "Book deleted successfully.";
            
        }
        else{
            return "Book not found.";
        }
        
    }

}
