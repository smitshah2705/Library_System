package com.librarysystem.library_system;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class LibrarySystemControllerTests {

    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private UserService userService;

    @Autowired
    private BookController bookController;

    @Autowired
    private UserController userController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookController, userController).build();
    }

    // BookController Tests
    @Test
    public void testGetAllBooks() throws Exception {
        when(bookService.getAllBooks()).thenReturn(Arrays.asList(
                new Book("Title 1", "Author 1"),
                new Book("Title 2", "Author 2")
        ));

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Title 1"))
                .andExpect(jsonPath("$[1].title").value("Title 2"));
    }

    @Test
    public void testGetBookById() throws Exception {
        Book book = new Book("Title", "Author");
        when(bookService.getBookById(1)).thenReturn(Optional.of(book));

        mockMvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Title"))
                .andExpect(jsonPath("$.author").value("Author"));
    }

    @Test
    public void testAddBook() throws Exception {
        Book book = new Book("New Book", "New Author");
        when(bookService.addBook(book)).thenReturn(book);

        mockMvc.perform(post("/books")
                        .contentType("application/json")
                        .content("{\"title\": \"New Book\", \"author\": \"New Author\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("New Book"))
                .andExpect(jsonPath("$.author").value("New Author"));
    }

    // UserController Tests
    @Test
    public void testLoginAdmin() throws Exception {
        LoginRequest loginRequest = new LoginRequest("admin", "password");
        when(userService.authenticateUser("admin", "password")).thenReturn("admin");

        mockMvc.perform(post("/users/login")
                        .contentType("application/json")
                        .content("{\"username\": \"admin\", \"password\": \"password\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Welcome, admin!"));
    }

    @Test
    public void testLoginStudent() throws Exception {
        LoginRequest loginRequest = new LoginRequest("student", "password");
        when(userService.authenticateUser("student", "password")).thenReturn("student");

        mockMvc.perform(post("/users/login")
                        .contentType("application/json")
                        .content("{\"username\": \"student\", \"password\": \"password\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Welcome, student!"));
    }
}