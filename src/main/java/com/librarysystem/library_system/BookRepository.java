// This will play a keyrole in interacting with the databse

package com.librarysystem.library_system;

import java.util.List; // Will ouput it as a List
import org.springframework.data.jpa.repository.JpaRepository; // Provide basic function to perform on the data in the database
import com.librarysystem.library_system.Book; // This imports the Book enitity whihc it will manage

public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByAuthor(String author);
    List<Book> findByIsAvailable(boolean isAvailable);
    List<Book> findByTitle(String title);
}
