import axios from "axios"
import cookie from 'react-cookies'

const BASE_URL = 'http://localhost:8080/JobSearchWebApp/api/';

export const endpoints = {
    'categories': '/categories',
    'jobpostings': '/jobpostings',
    'register': '/users',
    'login': 'logins',
    'current-user': '/secure/profile',
    //'deleteJob': '/api/jobpostings',
}

export const authApis = () => {//object để tạo request có token
    const token = cookie.load('token');
    console.log("authApis - Token from cookie:", token); // Debug
    return axios.create({
        baseURL: BASE_URL,
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${cookie.load('token')}`,
        }
    })
}

export default axios.create({
    baseURL: BASE_URL,
    headers: {
        'Content-Type': 'application/json'
    }
})