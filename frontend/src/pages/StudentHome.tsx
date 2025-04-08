import { useState } from "react";
import "./StudentHome.css";

function StudentHome() {
    const [bookname, setBookname] = useState(""); //a state for input of the book title
    const [books, setBooks] = useState<{ id: number; title: string; author: string; isAvailable: boolean }[]>([]); //a state to hold the books returned from the search
    const username = localStorage.getItem("username") || ""; // this gets the username inputed in the login page

    const handleSearch = async () => {
        try {
               //(this is the actual data to be fetched)
            /*const response = await fetch(`http://localhost:8080/books/title/${encodeURIComponent(bookname)}`); 
            const data = await response.json();
            setBooks(data)*/
            
            
            // Sample data for testing
            const data = [
                { id: 123, title: "Harry Potter", author: "J.K. Rowling", isAvailable: true },
                { id: 141, title: "1984", author: "George Orwell", isAvailable: true },
                { id: 531, title: "Lord of the Rings", author: "J.R.R. Tolkien", isAvailable: false }
            ];
            const filtered = data.filter(book => book.title.includes(bookname)); //filters by bookname
            setBooks(filtered) //update state with the results
            
        } catch (error) {
            alert("Sorry! Something went wrong!");
        }
    };

    const handleBorrow = async (bookId: number) => {
        //Code to use for integration with backend and database
        /*try {
        const response = await fetch(`http://localhost:8080/books/${bookId}/borrow?studentName=${encodeURIComponent(username)}`, {
            method: "PUT"
        });
        alert("Book borrowed successfully!");
        }
        catch (error) {
            alert("Error Occured, unable to borrow book")
        }*/
       alert("Book borrowed successfully!");  //simulated alert
    }


    return (
        <div className="student-home">
            <img src="/teslogotransparent.png" alt="TES" width="200" className="logo" />
            <h1>Taipei European School Digital Library</h1>
            {/*user instructions*/}
            <p>Use the search bar below to search which book you would like to borrow.</p>
            <p>If you would like to see your current list of books, click the MyBookCollection button.</p>
            {/*search button*/}
            <input type="text" placeholder="Search for a book..." value={bookname} onChange={(e) => setBookname(e.target.value)}/> 
            <button onClick={handleSearch}>Search</button>
            
            {/* Display the list of books */}
            <div className="book-list">
                {books.length > 0 ? (
                    <ul>
                        {books.map((book, index) => (
                            <li key={index}> 
                                {book.title} - {book.author}
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
