import './AdminHome.css';

function AdminHome() {
    return (
        <div className="admin-home">
            <img src="/teslogotransparent.png" alt="TES" width="200" className="logo"/>
            <h1>Taipei European School Digital Library</h1>
            <p>Use the search bar below to search which book you would like to borrow/add/remove.</p>
            <p>If you would like to see your current list of books, click the MyBookCollection button</p>
            <input type="text" placeholder="Search for a book..."/>
            <button>Search</button>
        </div>
    );
}
export default AdminHome;