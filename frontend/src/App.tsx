import { useState } from 'react'
import './App.css'
import Login from "./pages/Login"

function App() {
  const [currentPage, setCurrentPage] = useState("home");
  return (
    <>
      {currentPage === "home" ? (
        <>
          <img src="/teslogo.jpg" alt="TES" width="100" className="logo"/>
          <h1>Welcome to the TES Library!</h1>
          <p>
            Manage your books here!
          </p>
          <button onClick={() => setCurrentPage("login")}>Go to Login</button>
        </>
      ) : (
        <>
          <Login />
          <button onClick={() => setCurrentPage("home")}>Back to Home</button>
        </>
      )}
    </>
  )
}

export default App
