import './App.css';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Header from './components/layout/Header';
import Home from './components/Home';
import 'bootstrap/dist/css/bootstrap.min.css';
import Login from './components/Login';
import JobList from './components/JobList';
import Register from './components/Register';
import Profile from './components/Profile';
import MyCartReducer from "./reducers/MyCartReducer";
import MyUserReducer from "./reducers/MyUserReducer";
import { MyCartContext, MyDispatchContext, MyUserContext, UserProvider } from './configs/Contexts';
import { useReducer } from 'react';


const App = () => {
  console.log("App is rendering");
  //const [user, dispatch] = useReducer(MyUserReducer, null);
  //const [cart, cartDispatch] = useReducer(MyCartReducer, 0);
  return (
    <UserProvider> {/* Sử dụng UserProvider thay vì tự quản lý useReducer */}
      <BrowserRouter>
        {/* //viết trong đây sẽ là cố định( k thay đổi trong rất cả  điều hướng) */}
        <Header />
        <Routes>
          {/* viết trong routes này sẽ thay đổi */}
          <Route path="/" element={<Home />} />
          <Route path="/logins" element={<Login />} />
          <Route path="/users" element={<Register />} />
          {/* <Route path="/secure/profile" element={<Profile />} /> */}
        </Routes>

        <JobList />
      </BrowserRouter>
    </UserProvider>
  );
}

export default App;
