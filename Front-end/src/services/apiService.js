import axios from 'axios';
import toast from "react-hot-toast";

// Создаем экземпляр axios
const api = axios.create({
    baseURL: process.env.REACT_APP_API_URL,
});


// Interceptor для установки заголовка авторизации
api.interceptors.request.use(request => {
    let userData = JSON.parse(localStorage.getItem("userData"));
    if (userData && userData.token) {
        request.headers['Authorization'] = `Bearer ${userData.token}`;
    }
    console.log('Starting Request', JSON.stringify(request, null, 2));
    return request;
});

export function setAuthToken(token) {
    api.defaults.headers.common['Authorization'] = `Bearer ${token}`;
}

export function clearAuthToken() {
    delete api.defaults.headers.common['Authorization'];
}

export async function loginUser(credentials) {
    try {
        const response = await api.post(`/auth/authenticate`, credentials);
        return response.data;
    } catch (error) {
        toast.error(error?.response?.data?.errors?.[0]);
        console.error(error);
    }
}

export async function fetchBusinessStatistics(businessId) {
    try {
        const response = await api.get(`/business/${businessId}/statistic`);
        return response.data;
    } catch (error) {
        toast.error(error?.response?.data?.errors?.[0]);
        console.error(error);
    }
}

export async function fetchUserProfile(userId) {
    try {
        const response = await api.get(`user/${userId}/profile`);
        return response.data;
    } catch (error) {
        toast.error(error?.response?.data?.errors?.[0]);
        console.error(error);
    }
}

export async function fetchTemplate(templateId) {
    try {
        const response = await api.get(`templates/${templateId}`);
        return response.data;
    } catch (error) {
        toast.error(error?.response?.data?.errors?.[0]);
        console.error(error);
    }
}

export async function fetchBusinessSupportedRequirements(businessId) {
    try {
        const response = await api.get(`/business/${businessId}/requirements`);
        return response.data;
    } catch (error) {
        toast.error(error?.response?.data?.errors?.[0]);
        console.error(error);
    }
}

export async function postNewRequirement(businessId, requirements) {
    try {
        console.log(requirements)
        const response = await api.post(`/business/${businessId}/requirements`, requirements);
    } catch (error) {
        toast.error(error?.response?.data?.errors?.[0]);
        console.error(error);
    }
}

export async function fetchRequirementsByCategory(categoryId) {
    try {
        const response = await api.get(`/templates/categories/${categoryId}/requirement`);
        return response.data;
    } catch (error) {
        toast.error(error?.response?.data?.errors?.[0]);
        console.error(error);
    }
}

export async function deleteRequirement(businessId, requireentId) {
    try {
        const response = await api.delete(`/business/${businessId}/requirements/${requireentId}`);
        return response.data;
    } catch (error) {
        toast.error(error?.response?.data?.errors?.[0]);
        console.error(error);
    }
}

export async function fetchBusinessTerminals(businessId) {
    try {
        const response = await api.get(`/business/${businessId}/terminals`);
        return response.data;
    } catch (error) {
        toast.error(error?.response?.data?.errors?.[0]);
        console.error(error);
    }
}

export async function postNewTerminal(teminals) {
    try {
        const response = await api.post(`/business/terminals`, teminals);
        return response.data;
    } catch (error) {
        toast.error(error?.response?.data?.errors?.[0]);
        console.error(error);
    }
}

export async function deleteTerminal(businessId, termainalId) {
    try {
        await api.delete(`/business/${businessId}/terminals/${termainalId}`);
    } catch (error) {
        toast.error(error?.response?.data?.errors?.[0]);
        console.error(error);
    }
}

export async function getBusinessByUser(userId) {
    try {
        const response = await api.get(`/business/user/${userId}`);
        return response.data;
    } catch (error) {
        toast.error(error?.response?.data?.errors?.[0]);
        console.error(error);
    }
}

export async function createEmployee(data, businessId) {
    try {
        const response = await api.post(`/business/${businessId}/employee`, data);
        return response.data;
    } catch (error) {
        toast.error(error?.response?.data?.errors?.[0]);
        console.error(error);
        throw new error;
    }
}


export async function getEmployees(businessId) {
    try {
        const response = await api.get(`/business/${businessId}/employee`);
        return response.data;
    } catch (error) {
        toast.error(error?.response?.data?.errors?.[0]);
        console.error(error);
    }
}

export async function updateUserProfile(userId, data) {
    try {
        const response = await api.put(`user/${userId}/profile`, data);
        return response.data;
    } catch (error) {
        toast.error(error?.response?.data?.errors?.[0]);
        console.error(error);
    }
}

export async function deleteUser(userId, businessId) {
    try {
        await api.delete(`business/${businessId}/employee/${userId}`);

    } catch (error) {
        toast.error(error?.response?.data?.errors?.[0]);
        console.error(error);
    }
}

export async function getAllCategories() {
    try {
       const response =  await api.get(`/templates/categories`);
        return response.data
    } catch (error) {
        toast.error(error?.response?.data?.errors?.[0]);
        console.error(error);
    }
}

export async function registerUser(data) {
    try {
        const response = await api.post(`/auth/register`, data);
        return response.data
    } catch (error) {
        toast.error(error?.response?.data?.errors?.[0]);
        console.error(error);
    }
}

export async function createBusiness(data) {
    try {
        const response = await api.post(`/business`, data);
        return response.data
    } catch (error) {
        toast.error(error?.response?.data?.errors?.[0]);
        console.error(error);
    }
}