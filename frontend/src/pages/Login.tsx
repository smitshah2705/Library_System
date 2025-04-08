import { useState } from "react";
import './Login.css';
import StudentHome from "./StudentHome";
import AdminHome from "./AdminHome";
function Login() {
    const [username, setUsername] = useState(""); //stores username input
    const [password, setPassword] = useState(""); // stores password input
    const [userRole, setUserRole] = useState(""); // stores role of logged in user

    const handleLogin = async () => {
        try {
            //Code for when connecting to backend
            /*const response = await fetch("http://localhost:8080/users/login", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ username, password }),
            });
            
            const data = await response.json();
            
            localStorage.setItem("username", data.username);*/
            //code for testing
            const data = "Welcome, admin!" //placeholder 

            if (data === "Welcome, admin!") { 
                setUserRole("admin"); //set role as admin if that is the response that is received
            } else if (data === "Welcome, student!") {
                setUserRole("student"); // set role as student if that is the response that is recieved 
            } else {
                alert("Invalid credentials. Please try again."); 
            }
        } catch (error) {
            console.error("Login error:", error); //log and alert any errors
            alert("An error occurred. Please try again.");
        }
    };

    if (userRole === "admin") {
        return <AdminHome />; //navigate user to adminhome page 
    } else if (userRole === "student") {
        return <StudentHome />; //navigate user to studenthome page
    }
    return (
        <div>
            <img src="/teslogotransparent.png" alt="TES" width="200" className="logo"/>
            <div>
                <h1>Login</h1>
                <p>Please enter your username and password:</p>
            </div>
            <div className="box">
                    <div className="input-group">
                        <label className="text">Username: </label>
                        <input type="text" value={username} onChange={(e) => setUsername(e.target.value)} className="input-field"/>
                    </div>
                    <div className="input-group">
                        <label className="text" >Password: </label>
                        <input type="text"value={password} onChange={(e) => setPassword(e.target.value)} className="input-field"/>
                    </div>
                    <div>
                        <button className="small-button" onClick={handleLogin}>Submit</button>
                    </div>
                <p></p>
            </div>
        </div>
    );
}
export default Login;