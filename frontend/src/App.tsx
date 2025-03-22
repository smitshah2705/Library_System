import { Routes, Route } from "react-router-dom";
import Home from "./pages/Home";
import Login from "./pages/Login";
import MyBookCollection from "./pages/MyBookCollection";

function App() {
  return (
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/login" element={<Login />} />
      <Route path="/my-books" element={<MyBookCollection />} />
    </Routes>
  );
}

export default App;
