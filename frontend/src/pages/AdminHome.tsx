import { useState } from "react";
import "./AdminHome.css";

function AdminHome() {
    const [bookname, setBookname] = useState("");
    const [books, setBooks] = useState<{ id: number; title: string; author: string; isAvailable: boolean }[]>([]);
    const username = localStorage.getItem("username") || "";
    const [newTitle, setNewTitle] = useState("");
    const [newAuthor, setNewAuthor] = useState("");
    const [newAvailable, setNewAvailable] = useState(true);
    const data = [
        { id: 123, title: "Harry Potter", author: "J.K. Rowling", isAvailable: true },
        { id: 141, title: "1984", author: "George Orwell", isAvailable: true },
        { id: 531, title: "Lord of the Rings", author: "J.R.R. Tolkien", isAvailable: false }
        ];
    const handleSearch = async () => {
        try {
            /*   (this is the actual data to be fetched)
           const response = await fetch(`http://localhost:8080/books/title/${encodeURIComponent(bookname)}`); 
            const data = await response.json();
            setBooks(data)
            */
            
            /* Sample data for testing*/
            
            const filtered = data.filter(book => book.title.includes(bookname));
            setBooks(filtered)
            
        } catch (error) {
            alert("Sorry! Something went wrong!");
        }
    };

    const handleBorrow = async (bookId: number) => {
        try {
        const response = await fetch(`http://localhost:8080/books/${bookId}/borrow?studentName=${encodeURIComponent(username)}`, {
            method: "PUT"
        });
        alert("Book borrowed successfully!");
    }
        catch (error) {
            alert("Error Occured, unable to borrow book")
        }
    }
    const remove = async (bookId: number) => {
        try {
            const response = await fetch(`http://localhost:8080/books/${bookId}`, {
                method: "DELETE"
            });
        alert("Book removed")
        }
        catch (error) {
            alert("Error Occured, unable to remove book")
        }
    }
    const addBook = async (Title: string, Author: string, IsAvailable: boolean) => {
        /*try {
            const response = await fetch("http://localhost:8080/books", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ title, author, isAvailable })
            });
            const data = await response.json();
            alert("Book added!");
            setNewTitle("");
            setNewAuthor("");
            setNewAvailable(true);
        } catch (error) {
            alert("Failed to add book");
        }*/
        data.push({id: 1122,title: Title,author: Author,isAvailable: IsAvailable});
        alert("Book added!");
    };

    return (
        <div className="admin-home">
            <img src="/teslogotransparent.png" alt="TES" width="200" className="logo" />
            <h1>Taipei European School Digital Library</h1>
            <p>Use the search bar below to search which book you would like to borrow/add/remove.</p>
            <p>If you would like to see your current list of books, click the MyBookCollection button.</p>
            <input type="text" placeholder="Search for a book..." value={bookname} onChange={(e) => setBookname(e.target.value)}/>
            <button onClick={handleSearch}>Search</button>
            
            {/* Display the list of books */}
            <div className="book-list">
                {books.length > 0 ? (
                    <ul>
                        {books.map((book, index) => (
                            <li key={index}> 
                                 
                                <p>{book.title} - {book.author}</p>
                                
                                {book.isAvailable ? (
                                    <button onClick={() => handleBorrow(book.id)}>Borrow</button>
                                    
                                ) : (
                                    <p>Unavailable</p>
                                )}
                                <button onClick={() => remove(book.id)}>Remove</button>
                            </li>
                        ))}
                    </ul>
                ) : (
                    <p>No books found.</p>
                )}
            </div>
            <div>
                <h2>Add Book Section</h2>
                <input type="text" placeholder="Enter Book Title" value={newTitle} onChange={(e) => setNewTitle(e.target.value)}/>
                <input type="text" placeholder="Enter Author's Name" value={newAuthor} onChange={(e) => setNewAuthor(e.target.value)}/>
                <input type="checkbox" checked={newAvailable}  onChange={() => setNewAvailable(!newAvailable)}/>
                <button onClick={() => addBook(newTitle,newAuthor,newAvailable)}>Add</button>
            </div>
        </div>
    );
}

export default AdminHome;