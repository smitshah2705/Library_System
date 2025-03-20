// Here we define actions that we can perform on the Book entity. There are already default actions, I added custom ones.
package com.librarysystem.library_system;

import java.util.List; // Will ouput it as a List

import org.springframework.data.jpa.repository.JpaRepository; // Provide basic function to perform on the data in the database

public interface BookRepository extends JpaRepository<Book, Integer> { // JPaRepository <> means it will work with the Book entity and that the primary key type of Book is Integer
    List<Book> findByAuthor(String author);
    List<Book> findByIsAvailable(boolean isAvailable);
    List<Book> findByTitle(String title);
    List<Book> findByBorrowedby(User user);
}
