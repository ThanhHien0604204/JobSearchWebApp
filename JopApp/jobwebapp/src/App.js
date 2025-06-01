import './App.css';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Header from './components/layout/Header';
import Home from './components/Home';
import 'bootstrap/dist/css/bootstrap.min.css';
import Login from './components/Login';
import JobList from './components/JobList';
import Register from './components/Register';
import Profile from './components/Profile';
const App = () => {
  return (
     <BrowserRouter>
     {/* //viết trong đây sẽ là cố định( k thay đổi trong rất cả  điều hướng) */}
      <Header />
      <Routes>
        {/* viết trong routes này sẽ thay đổi */}
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/users" element={<Register />} />
        <Route path="/secure/profile" element={<Profile />} />
      </Routes>

      <JobList />
    </BrowserRouter>
  );
}

export default App;
