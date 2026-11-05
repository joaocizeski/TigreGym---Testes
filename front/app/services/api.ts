'use client'

import axios from "@/node_modules/axios/index";
import { store } from "../redux/store";
import { logout } from "../redux/slices/authSlice";

const api = axios.create({
    baseURL:'http://localhost:8080'
});

api.interceptors.request.use(
    (config) =>{
        const token = store.getState().auth.token;

        if(token){
            config.headers.Authorization = `Bearer ${token}`;
        }

        return config;
    }
);

api.interceptors.response.use(
    (response) => response,
    (error) => {
        if(error.response?.status === 401){
            store.dispatch(logout());
        }

        return Promise.reject(error);
    }
);

export default api;
