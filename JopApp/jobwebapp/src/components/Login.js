import React, { useState } from 'react';
import { Button, Form, Col, Row, Alert,Container } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { endpoints } from '../configs/Api';
import cookie from 'react-cookies';

const Login = () => {
    const [formData, setFormData] = useState({ username: '', password: '' });
    const [message, setMessage] = useState('');
    const navigate = useNavigate();

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post(endpoints.login, formData); // Gửi JSON body
            if (response.data.success) {
                cookie.save('token', response.data.token, { path: '/' });
                setMessage(response.data.message);
                navigate('/');
            }
        } catch (error) {
            setMessage(error.response?.data?.message || 'Đăng nhập thất bại.');
        }
    };

    return (
        <Container className="my-4">
            <h2>Đăng nhập</h2>
            {message && <Alert variant={message.includes('thành công') ? 'success' : 'danger'}>{message}</Alert>}
            <Form onSubmit={handleSubmit}>
                <Row className="g-3">
                    <Col md={6}>
                        <Form.Group>
                            <Form.Label>Tên đăng nhập</Form.Label>
                            <Form.Control
                                type="text"
                                name="username"
                                value={formData.username}
                                onChange={handleInputChange}
                                required
                            />
                        </Form.Group>
                    </Col>
                    <Col md={6}>
                        <Form.Group>
                            <Form.Label>Mật khẩu</Form.Label>
                            <Form.Control
                                type="password"
                                name="password"
                                value={formData.password}
                                onChange={handleInputChange}
                                required
                            />
                        </Form.Group>
                    </Col>
                    <Col md={12}>
                        <Button type="submit" variant="primary">Đăng nhập</Button>
                    </Col>
                </Row>
            </Form>
        </Container>
    );
};

export default Login;