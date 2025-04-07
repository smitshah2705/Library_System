import "./MyBookCollection.css";
import { useState } from "react";
function MyBookCollection() {
    const [borrowed, setBorrowed] = useState<{ id: number; title: string; author: string }[]>([]);
    const username = localStorage.getItem("username") || "";

    //data for testing
    const simulatedData = [
        { id: 1, title: "Mock Book 1", author: "Mock Author 1"},
        { id: 2, title: "Mock Book 2", author: "Mock Author 2"},
    ];

    const handleBorrowed = async () => {
        //To be used when backend connected
        /*try {
            const response = await fetch(`http://localhost:8080/users/${encodeURIComponent(username)}/borrowed-books`);
            const data = await response.json();
            setBorrowed(data);
        }
        catch (error) {
            alert("Something went wrong!")
        }*/
        setBorrowed(simulatedData)
    }

    const handleReturn = async (bookID: number) => {
        /*try {
            const response = await fetch(`http://localhost:8080/books/${bookID}/return`, { method: "PUT" });
            alert("Book returned!");
            handleBorrowed();
        }
        catch (error) {
            alert("Error occured, unable to return book")
        }*/
        setBorrowed((prev) => prev.filter((book) => book.id !== bookID));
        alert("Book returned!");
    }
    const isOverDue = async (username: String) => {
        //This will be the code that will be used when it is connected with backend and database
        /*try{
            const response = await fetch (`http://localhost:8080/books/checkoverdue/${username}`, {method: "PUT"});
            const data = await response.json();
            return data;
        }
        catch (error) {
            alert("Error checking whether overdue")
        }*/
        //Temporay code for testing
        alert("Books Checked for overdue")
    }
    
    return (
        <div>
            <img src="/teslogotransparent.png" alt="TES" width="200" className="logo" />
            <h1>My Books:</h1>
            <button onClick={handleBorrowed}>Load Borrowed Books</button>
            <button onClick={() => isOverDue(username)}>Check for Overdue Books</button>
            <div className="box">
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