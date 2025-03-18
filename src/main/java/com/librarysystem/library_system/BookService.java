package com.librarysystem.library_system;

import org.springframework.stereotype.Service;  // Import the @Service annotation to mark this class as a Spring service - whihc means this code contains logic

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired; // Used to inject dependency 

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

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

    public String borrowBook(int id, String studentName)
    {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));

        if(!book.isAvailable())
        {
            return "Book is already reserved.";
        }

        book.setIsAvailable(false); // Set the book as unavailable
        book.setBorrowedBy(studentName); // Set the student who borrowed the book
        book.setBorrowedDate(new java.util.Date()); // Set the borrow date. creates a new Date object that represents the current date and time 
        bookRepository.save(book); // Save the updated book record
        return "Book borrowed successfully.";
    }

    public String returnBook(int id)
    {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));

        if(book.isAvailable())
        {
            return "Book has already been returned.";
        }

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
