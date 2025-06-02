import React, { useContext, useEffect, useState } from 'react';
import { Button, Container, Nav, Navbar, NavDropdown, Image } from 'react-bootstrap';
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';
import { endpoints, authApis } from '../../configs/Api';
import cookie from 'react-cookies';
import { MyDispatchContext, MyUserContext } from '../../configs/Contexts';

const Header = () => {
  const [categories, setCategories] = useState([]);
  const user = useContext(MyUserContext);
  const nav = useNavigate();
  const [kw, setKw] = useState();
  const dispatch = useContext(MyDispatchContext);

  useEffect(() => {
    // Lấy danh sách danh mục
    axios.get(endpoints.categories)
      .then((response) => {
        setCategories(response.data);
      })
      .catch((error) => console.error('Lỗi khi tìm danh mục:', error));

    // // Lấy thông tin người dùng
    // const token = cookie.load('token');
    // if (token) {
    //   authApis(token).get(endpoints['current-user'])
    //     .then((response) => {
    //       setUser(response.data);
    //     })
    //     .catch((error) => {
    //       console.error('Lỗi khi tìm kiếm người dùng:', error);
    //       cookie.remove('token'); // Xóa token nếu không hợp lệ
    //       setUser(null);
    //     });
    // }
  }, []);

  // const handleLogout = () => {
  //   cookie.remove('token');
  //   setUser(null);
  //   nav('/login');
  // };
  const handleLoginRegister = () => {
    nav('/login'); // Hoặc mở modal đăng nhập/đăng ký nếu cần
  };
  return (
    <Navbar expand="lg" bg="light" variant="light" className="shadow-sm py-3">
      <Container>
        <Navbar.Brand as={Link} to="/" className="fw-bold text-dark">
          <h1 className="display-6 d-inline">JobSearch</h1>
          <span className="ms-2 text-muted">Tìm việc dễ dàng</span>
        </Navbar.Brand>

        {/* Liên kết giữa */}
        <Nav className="mx-auto">
          <Link to="/" className="nav-link">Trang chủ</Link>
        </Nav>

        {/* <NavDropdown title="Danh mục" id="categoryDropdown">
            {categories.map((c) => (
              <NavDropdown.Item
                key={c.id}
                as={Link}
                to={`/?categoryId=${c.id}`}
              >
                {c.name}
              </NavDropdown.Item>
            ))}
          </NavDropdown> */}
        {user === null ? (
          <>
            <Link to="/users" className="nav-link text-success">Đăng ký</Link>
            <Link to="/logins" className="nav-link text-danger">Đăng nhập</Link>
          </>
        ) : (
          <>
            <Link to="/" className="me-3">
              <Image
                src={user.avatar || 'https://via.placeholder.com/40'}
                alt="Avatar"
                roundedCircle
                className="me-2"
                width="40"
              />
              Chào {user.username}!
            </Link>
            {user.role === 'EMPLOYER' && (
              <Link to="/jobpostings" className="nav-link text-danger">Đăng bài</Link>
            )}
            <Button variant="danger" onClick={() => dispatch({ "type": "logout" })} className="rounded-pill">
              Đăng xuất
            </Button>
          </>
        )}
      </Container>
    </Navbar>
  );
};

export default Header;