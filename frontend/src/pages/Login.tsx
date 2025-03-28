import { useState } from "react";
function Login() {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    
    return (
        <div>
            <img src="/teslogotransparent.png" alt="TES" width="200" className="logo"/>
            <h1>Login</h1>
            <p>Please enter your username and password:</p>
        </div>
    );
}
export default Login;