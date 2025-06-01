import React, { useEffect, useState } from 'react';
import { Button, Container, Nav, Navbar, NavDropdown, Image } from 'react-bootstrap';
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';
import { endpoints, authApis } from '../../configs/Api';
import cookie from 'react-cookies';

const Header = () => {
  const [categories, setCategories] = useState([]);
  const [user, setUser] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    // Lấy danh sách danh mục
    axios.get(endpoints.categories)
      .then((response) => {
        setCategories(response.data);
      })
      .catch((error) => console.error('Lỗi khi tìm danh mục:', error));

    // Lấy thông tin người dùng
    const token = cookie.load('token');
    if (token) {
      authApis(token).get(endpoints.currentUser)
        .then((response) => {
          setUser(response.data);
        })
        .catch((error) => {
          console.error('Lỗi khi tìm kiếm người dùng:', error);
          cookie.remove('token'); // Xóa token nếu không hợp lệ
          setUser(null);
        });
    }
  }, []);

  const handleLogout = () => {
    cookie.remove('token');
    setUser(null);
    navigate('/login');
  };
  const handleLoginRegister = () => {
    navigate('/login'); // Hoặc mở modal đăng nhập/đăng ký nếu cần
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
          <Nav.Link as={Link} to="/" className="text-primary mx-3">
          Trang chủ
          </Nav.Link>
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
                <Nav.Link as={Link} to="/users" className="text-success">
                  Đăng ký
                </Nav.Link>
                <Nav.Link as={Link} to="/login" className="text-danger">
                  Đăng nhập
                </Nav.Link>
              </>
            ) : (
              <>
                <Nav.Link as={Link} to="/secure/profile" className="me-3">
                  <Image
                    src={user.avatar || 'https://via.placeholder.com/40'}
                    alt="Avatar"
                    roundedCircle
                    className="me-2"
                    width="40"
                  />
                  Chào {user.username}!
                </Nav.Link>
                {user.role === 'EMPLOYER' && (
                  <Nav.Link as={Link} to="/jobpostings" className="text-primary me-3">
                    Đăng bài
                  </Nav.Link>
                )}
                <Button variant="danger" onClick={handleLogout} className="rounded-pill">
                  Đăng xuất
                </Button>
              </>
            )}
      </Container>
    </Navbar>
  );
};

export default Header;