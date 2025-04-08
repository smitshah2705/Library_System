DROP TABLE IF EXISTS books;

CREATE TABLE books (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    borrowedby VARCHAR(255) NOT NULL,
    isAvailable BOOLEAN NOT NULL DEFAULT TRUE,
    borrowedDate DATE
);

INSERT INTO books (title, author, isAvailable, borrowedDate) VALUES
('Romeo and Juliet', 'William Shakespear', TRUE, NULL),
('Harry Potter and the Half Blood Prince', 'J.K Rowling', 'Charlie Brown', FALSE, '2025-04-01'),
('To Kill a Mockingbird', 'Harper Lee', TRUE, NULL),
('Brave New World', 'Aldous Huxley', TRUE, NULL),
('War and Peace', 'Leo Tolstoy', 'James Wild', FALSE, '2025-03-28'),
('Animal Farm', 'George Orwell', TRUE, NULL),
('The Great Gatsby', 'F. Scott Fitzgerald', TRUE, NULL),
('Jane Eyre', 'Charlotte Brontë', 'Tim Cheese', FALSE, '2025-04-03'),
('Moby-Dick', 'Herman Melville', TRUE, NULL),
('Frankenstein', 'Mary Shelley', TRUE, NULL);

SELECT * FROM books;
