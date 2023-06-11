import axios from 'axios';
import toast from "react-hot-toast";

const API_URL = process.env.REACT_APP_API_URL;

export async function loginUser(credentials) {
    try {
        const response = await axios.post(`${API_URL}/auth/authenticate`, credentials);
        return response.data;
    } catch (error) {
        toast.error(error?.response?.data?.errors?.[0]);
        console.error(error);
    }
}

export async function getUserData(userId) {
    try {
        const response = await axios.get(`${API_URL}/users/${userId}`);
        return response.data;
    } catch (error) {
        // Обработка ошибок
        console.error(error);
    }
}

// И так далее для других операций API...
