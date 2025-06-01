import axios from "axios"
import cookie from 'react-cookies'

const BASE_URL = 'http://localhost:8080/JobSearchWebApp/api/';

export const endpoints = {
    'categories': '/categories',
    'jobpostings': '/jobpostings',
    'register': '/users',
    'login': '/login',
    'current-user': '/secure/profile',
    //'deleteJob': '/api/jobpostings',
}

export const authApis = () => {//object để tạo request có token
    return axios.create({
        baseURL: BASE_URL,
        headers: {
            'Authorization': `Bearer ${cookie.load('token')}`
        }
    })
}

export default axios.create({
    baseURL: BASE_URL
})