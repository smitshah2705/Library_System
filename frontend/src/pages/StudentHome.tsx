import { useState } from "react";
import "./StudentHome.css";

function StudentHome() {
    const [bookname, setBookname] = useState("");
    const [books, setBooks] = useState<{ id: number; title: string; author: string; isAvailable: boolean }[]>([]);
    const username = localStorage.getItem("username") || "";

    const handleSearch = async () => {
        try {
            /*   (this is the actual data to be fetched)
           const response = await fetch(`http://localhost:8080/books/title/${encodeURIComponent(bookname)}`); 
            const data = await response.json();
            setBooks(data)
            */
            
            /* Sample data for testing*/
            const data = [
                { id: 123, title: "Harry Potter", author: "J.K. Rowling", isAvailable: true },
                { id: 141, title: "1984", author: "George Orwell", isAvailable: true },
                { id: 531, title: "Lord of the Rings", author: "J.R.R. Tolkien", isAvailable: false }
            ];
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


    return (
        <div className="student-home">
            <img src="/teslogotransparent.png" alt="TES" width="200" className="logo" />
            <h1>Taipei European School Digital Library</h1>
            <p>Use the search bar below to search which book you would like to borrow.</p>
            <p>If you would like to see your current list of books, click the MyBookCollection button.</p>
            <input type="text" placeholder="Search for a book..." value={bookname} onChange={(e) => setBookname(e.target.value)}/>
            <button onClick={handleSearch}>Search</button>
            
            {/* Display the list of books */}
            <div className="book-list">
                {books.length > 0 ? (
                    <ul>
                        {books.map((book, index) => (
                            <li key={index}> 
                                {book.title} 
                                {book.author}
                                {book.isAvailable ? (
                                    <button onClick={() => handleBorrow(book.id)}>Borrow</button>
                                ) : (
                                    <p>Unavailable</p>
                                )}
                            </li>
                        ))}
                    </ul>
                ) : (
                    <p>No books found.</p>
                )}
            </div>
        </div>
    );
}

export default StudentHome;
