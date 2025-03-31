import { useState } from "react";
import "./StudentHome.css";

function StudentHome() {
    const [bookname, setBookname] = useState("");
    const [books, setBooks] = useState<string[]>([]);

    const handleSearch = async () => {
        try {
            /*
            const response = await fetch("http://localhost:8080/users/search", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ bookname }),
            });
            const data = await response.json();
            */

            const data = ["book1", "book2", "book3"];
            setBooks(data);
        } catch (error) {
            alert("Sorry! Something went wrong!");
        }
    };

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
                            <li key={index}>{book}</li>
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
