DROP TABLE IF EXISTS books;

-- Create the books table
CREATE TABLE books (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    isAvailable BOOLEAN NOT NULL DEFAULT TRUE,
    borrowedDate DATE
);

-- Insert 10 books
INSERT INTO books (title, author, isAvailable, borrowedDate) VALUES
('1984', 'George Orwell', TRUE, NULL),
('The Hobbit', 'J.R.R. Tolkien', FALSE, '2025-04-01'),
('To Kill a Mockingbird', 'Harper Lee', TRUE, NULL),
('Brave New World', 'Aldous Huxley', TRUE, NULL),
('Fahrenheit 451', 'Ray Bradbury', FALSE, '2025-03-28'),
('Animal Farm', 'George Orwell', TRUE, NULL),
('The Great Gatsby', 'F. Scott Fitzgerald', TRUE, NULL),
('Jane Eyre', 'Charlotte Brontë', FALSE, '2025-04-03'),
('Moby-Dick', 'Herman Melville', TRUE, NULL),
('Frankenstein', 'Mary Shelley', TRUE, NULL);

SELECT * FROM books;
