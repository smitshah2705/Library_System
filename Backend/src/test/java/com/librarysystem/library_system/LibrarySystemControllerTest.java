package com.librarysystem.library_system;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest  // Loads Spring Boot context for testing
public class LibrarySystemControllerTests {

    @Test
    void sampleTest() {
        int expected = 1;
        int actual = 1;
        assertEquals(expected, actual, "The values should be equal.");
    }
}