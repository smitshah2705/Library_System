//Contains logic. uses actions from the repository and conditions to make rules. It will handle the rules and conditions before applying the actions on the data
//The controller can't directly connect with the reposiotry is because the controller's job is to handle HTTPrequest and it woul make the code too complicated ifyou also included business logic
// so to make it mroeclean and easy to manage Services classes are used

package com.librarysystem.library_system;

import org.springframework.stereotype.Service;  // Import the @Service annotation to mark this class as a Spring service - whihc means this code contains logic

import java.util.List; 
import java.util.Optional; //It holds exactly one value and it doesnt not necessarily contain a value.
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired; // Used to inject dependency 

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Book> getAllBooks()
    {
        return bookRepository.findAllBooks();
    }

    public Optional<Book> getBookById(Integer id) { 
        return bookRepository.findById(id); 
    }

    public List<Book> getBooksByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

     public List<Book> getBooksByAuthor(String author) {
        return bookRepository.findByAuthor(author);
     }

    public List<Book> getBooksByAvailability(boolean isAvailable) {
        return bookRepository.findByIsAvailable(isAvailable);
    }

    public List<Book> getBooksByUser(String username){
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        return bookRepository.findByBorrowedBy(user);
    }

    public List<Book> getBooksByOverDue()
    {
        return bookRepository.findOverDueBooks(true);
    }

    public List<Book> getBooksByOverDueForUser(String username)
    {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        return bookRepository.findOverDueBooksForUser(user,true);
    }

    public List<Book> checkOverdue(String username)
    {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        List<Book> borrowedbooks = user.getBorrowedBooks();

        for(Book book : borrowedbooks)
        {
            if(book.getDueDate().compareTo(new Date()) < 0)
            {
                book.setOverDue(true);
                user.getOverDueBooks().add(book);
            }
            else{
                book.setOverDue(false);
            }
            bookRepository.save(book);
        }
        userRepository.save(user);
        return user.getOverDueBooks();
    }

    public String borrowBook(Integer id, String Username)
    {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));

        if(!book.isAvailable())
        {
            return "Book is already reserved.";
        }

        User user = userRepository.findByUsername(Username).orElseThrow(() -> new RuntimeException("User not found"));


        book.setIsAvailable(false); 
        book.setBorrowedBy(user); // Set the ID of the student who borrowed the book
        book.setBorrowedDate(new Date()); // Set the new borrow date
        user.getBorrowedBooks().add(book); //Add the new book to the user borrowed list

        bookRepository.save(book); // Save the updated book db
        userRepository.save(user); //Save the user updated db
        return "Book borrowed successfully.";

    }

    public String returnBook(Integer id)
    {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));

        if(book.isAvailable())
        {
            return "Book has already been returned.";
        }

        if(book.getBorrowedDate()!= null)
        {
            User user = book.getBorrowedBy();

            user.getBorrowedBooks().remove(book);
            if(user.getOverDueBooks().contains(book))
            {
                user.getOverDueBooks().remove(book);
            }
            userRepository.save(user);

            book.setIsAvailable(true);
            book.setBorrowedBy(null);
            book.setBorrowedDate(null);
            book.setOverDue(false);
            bookRepository.save(book);
            return "Book has been returned successfully.";
        }
        else{
            return "This book has not been borrowed, or it's already returned.";
        }

    }

    public String addBook(Book book) {
        bookRepository.save(book);
        return "Book has been added successfully";
    }

    public String deleteBook(Integer id) {
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
