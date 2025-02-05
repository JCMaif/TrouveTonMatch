import { apiRequest } from "./apiRequest";
import { API_BASE_URL } from "../config/config";

export const createService = (resourcePath) => {
  const resourceUrl = `${API_BASE_URL}/${resourcePath}`;

  return {
    findAll: async (token) =>
        await apiRequest(resourceUrl, { method: "GET" }, token),
    findById: async (id, token) =>
        await apiRequest(`${resourceUrl}/${id}`, { method: "GET" }, token),
    delete: async (id, token) =>
        await apiRequest(`${resourceUrl}/${id}`, { method: "DELETE" }, token),
    update: async (id, token) =>
        await apiRequest(`${resourceUrl}/${id}`, {method: "PATCH"}, token),
    create: async (token) =>
        await apiRequest(`${resourceUrl}`, {method: "POST", body: JSON.stringify(token),})
  };
};
