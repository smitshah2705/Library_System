import { useState } from 'react'
import './App.css'
import Login from "./pages/Login"
import StudentHome from "./pages/StudentHome";
import AdminHome from "./pages/AdminHome";
import MyBookCollection from "./pages/MyBookCollection";

function App() {
  const [currentPage, setCurrentPage] = useState("home");
  return (
    <div
    style={{       //This is for the background image, which is not in the app.css file as it needs to be the same throughout all the pages in the website
      backgroundImage: "url('/bookbackground.jpg')",
      backgroundSize: "cover",  //This makes sure that the image covers the whole area
      backgroundPosition: "center", //Makes sure it is in the center
      backgroundAttachment: "fixed", //This keeps the background from moving when scrolling
      height: "100vh", 
      width: "100vw",
      display: "flex",                
      flexDirection: "column",        
      alignItems: "center",           //align horizontally 
      justifyContent: "center",       //align vertically
      color: "white",
      textShadow: "2px 2px 4px rgba(0,0,0,0.7)", //shadow to make it more visible 
    }}
    >
      {currentPage === "home" && (
        <>
          <img src="/teslogotransparent.png" alt="TES" width="200" className="logo"/>
          <h1>Welcome to the TES Library!</h1>
          <p>Manage your books here!</p>
          <p>Please login to continue</p>
          {/*buttons to navigate between pages, some are only used for testing */}
          <button onClick={() => setCurrentPage("login")}>Login</button>
          <button onClick={() => setCurrentPage("studenthome")}>studenthome (for testing)</button>
          <button onClick={() => setCurrentPage("adminhome")}>adminhome (for testing)</button>
        </>
      )} 
       {currentPage === "login" && (
        <>
          <Login />
          <button onClick={() => setCurrentPage("home")}>Back to Home</button>
        </>
      )}

      {currentPage === "studenthome" && (
        <>
          <StudentHome />
          <button onClick={() => setCurrentPage("home")}>Back to Home</button>
          <button className="nav-button" onClick={() => setCurrentPage("mybookcollection")}>My Book Collection</button>
        </>
      )}

      {currentPage === "adminhome" && (
        <>
          <AdminHome />
          <button onClick={() => setCurrentPage("home")}>Back to Home</button>
          <button className="nav-button" onClick={() => setCurrentPage("mybookcollection")}>My Book Collection</button>
        </>
      )}
      {currentPage === "mybookcollection" && (
        <>
          <MyBookCollection />
          <button onClick={() => setCurrentPage("home")}>Back to Home</button>
        </>
      )}

    </div>
  )
}

export default App
