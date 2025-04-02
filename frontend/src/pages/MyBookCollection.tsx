import "./MyBookCollection.css";
import { useState } from "react";
function MyBookCollection() {
    const [borrowed, setBorrowed] = useState<{ id: number; title: string; author: string; dueDate: string }[]>([]);
    const username = localStorage.getItem("username") || "";

    const handleBorrow = async () => {
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
            handleBorrow();
        }
        catch (error) {
            alert("Error occured, unable to return book")
        }
    }
    
    return (
        <div>
            <img src="/teslogotransparent.png" alt="TES" width="200" className="logo" />
            <h1>My Books:</h1>
            <button onClick={handleBorrow}>Load Borrowed Books</button>

        </div>
    );
}
export default MyBookCollection;