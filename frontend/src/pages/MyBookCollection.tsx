import "./MyBookCollection.css";
import { useState } from "react";
function MyBookCollection() {
    const [borrowed, setBorrowed] = useState<{ id: number; title: string; author: string }[]>([]);
    const username = localStorage.getItem("username") || "";

    const handleBorrowed = async () => {
        try {
            const response = await fetch(`http://localhost:8080/users/${encodeURIComponent(username)}/borrowed-books`);
            const data = await response.json();
            setBorrowed(data);
        }
        catch (error) {
            alert("Something went wrong!")
        }
    }

    const handleReturn = async (bookID: number) => {
        try {
            const response = await fetch(`http://localhost:8080/books/${bookID}/return`, { method: "PUT" });
            alert("Book returned!");
            handleBorrowed();
        }
        catch (error) {
            alert("Error occured, unable to return book")
        }
    }
    const isOverDue = async (username: String) => {
        try{
            const response = await fetch (`http://localhost:8080/books/checkoverdue/${username}`, {method: "PUT"});
            console.log(await response.text());
        }
        catch (error) {
            alert("Error checking whether overdue")
        }
    }
    
    return (
        <div>
            <img src="/teslogotransparent.png" alt="TES" width="200" className="logo" />
            <h1>My Books:</h1>
            <button onClick={handleBorrowed}>Load Borrowed Books</button>
            <button onClick={() => isOverDue(username)}>Check for Overdue Books</button>
            <div className="book-list">
                {borrowed.length > 0 ? (
                    <ul>
                        {borrowed.map((book) => (
                            <li key={book.id}>
                                {book.title} - {book.author}
                                <button onClick={() =>handleReturn(book.id)}>Return</button>
                            </li>
                        ))}
                    </ul>
                ):(
                    <p>No books currently borrowed</p>
                )}
            </div>
        </div>
    );
}
export default MyBookCollection;