import { apiRequest } from "./apiRequest";
import { API_BASE_URL } from "../config/config";

const createService = (resourcePath) => {
    const resourceUrl = `${API_BASE_URL}/${resourcePath}`;

    return {
        findAll: async (token) =>
            await apiRequest(resourceUrl, { method: "GET" }, token),
        findById: async (id, token) =>
            await apiRequest(`${resourceUrl}/${id}`, { method: "GET" }, token),
        delete: async (id, token) =>
            await apiRequest(`${resourceUrl}/${id}`, { method: "DELETE" }, token),
        update: async (id, data, token) =>
            await apiRequest(`${resourceUrl}/${id}`, {
                method: "PATCH",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(data),
            }, token),
        create: async (data, token) =>
            await apiRequest(resourceUrl, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(data),
            }, token),
    };
};

export const plateformeService = createService("plateforme");
export const enumService = createService("enum");

export const projetService = {
    ...createService("projet"),
    create: async (data, token) => {
        const url = `${API_BASE_URL}/projet`;
        return await apiRequest(url, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`,
            },
            body: JSON.stringify(data),
        }, token);
    },
};

export const userService = {
    ...createService("user"),
    getUsersByRole: async (role, token) => {
        const url = `${API_BASE_URL}/user/by-role?role=${role}`;
        return await apiRequest(url, { method: "GET" }, token);
    },
    patch: async (userId, data, token) => {
        const url = `${API_BASE_URL}/user/${userId}`;
        return await apiRequest(url, {
            method: "PATCH",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data),
        }, token);
    },
    getRoles: async () => {
        const url = `${API_BASE_URL}/enum/roles`;
        return await apiRequest(url, { method: "GET" });
    },
    create: async (data, token) => {
        const url = `${API_BASE_URL}/user/signup`;
        return await apiRequest(url, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`,
            },
            body: JSON.stringify(data),
        }, token);
    },
    finaliser: async (userId, data, token) => {
        const url = `${API_BASE_URL}/user/${userId}/complete-profile`;
        return await apiRequest(url, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`,
            },
            body: JSON.stringify(data),
        }, token);
    },
    toggleParrainActive: async (userId, data, token) => {
        const url = `${API_BASE_URL}/parrains/${userId}/toggle-active`;
        return await apiRequest(url, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`,
            },
            body: JSON.stringify(data),
        }, token);
    },
};


