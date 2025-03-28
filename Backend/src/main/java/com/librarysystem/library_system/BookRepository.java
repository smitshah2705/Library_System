// Here we define actions that we can perform on the Book entity. Crud Repository brings in basic CRUD functions but I can add my own in
package com.librarysystem.library_system;

import java.util.List; // Will ouput it as a List

import org.springframework.data.repository.CrudRepository; // Provide basic function to perform on the data in the database

public interface BookRepository extends CrudRepository<Book, Integer> { // CrudRepository <> means it will work with the Book entity and that the primary key type of Book is Integer
    List<Book> findByAuthor(String author);
    List<Book> findByIsAvailable(boolean isAvailable);
    List<Book> findByTitle(String title);
    List<Book> findByBorrowedby(User user);
    List<Book> findAllBooks();
    List<Book> findOverDueBooks(boolean overdue);//Spring uses the method name to infer the query so it wont mix it up with the availability boolean
    List<Book> findOverDueBooksForUser(User user, boolean overdue);
}
