import { useState } from 'react'
import './App.css'
import Login from "./pages/Login"

function App() {
  const [currentPage, setCurrentPage] = useState("home");
  return (
    <div
    style={{
      backgroundImage: "url('/bookbackground.jpg')",
      backgroundSize: "cover",
      backgroundPosition: "center",
      backgroundAttachment: "fixed",
      height: "100vh",
      width: "100vw",
      display: "flex",                
      flexDirection: "column",        
      alignItems: "center",            
      justifyContent: "center",
      color: "white",
      textShadow: "2px 2px 4px rgba(0,0,0,0.7)",
    }}
    >
      {currentPage === "home" ? (
        <>
          <img src="/teslogotransparent.png" alt="TES" width="200" className="logo"/>
          <h1>Welcome to the TES Library!</h1>
          <p>Manage your books here!</p>
          <p>Please login to continue</p>
          <button onClick={() => setCurrentPage("login")}>Login</button>
        </>
      ) : (
        <>
          <Login />
          <button onClick={() => setCurrentPage("home")}>Back to Home</button>
        </>
      )}
    </div>
  )
}

export default App
