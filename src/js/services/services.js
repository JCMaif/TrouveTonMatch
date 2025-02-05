import { createService } from "./apiServiceFactory";
import { apiRequest } from "./apiRequest";
import { API_BASE_URL } from "../config/config";

export const plateformeService = createService("plateforme");
export const projetService = createService("projet");
export const enumService = createService("enum");

export const userService = {
  ...createService("user"),

  getUsersByRole: async (role, token) => {
    const url = `${API_BASE_URL}/user/by-role?role=${role}`;
    return await apiRequest(url, { method: "GET" }, token);
  },

  patch: async (userId, data, token) => {
    const url = `${API_BASE_URL}/user/${userId}`;
    return await apiRequest(url,
        {
          method: "PATCH",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data),
        },
        token
    );
  },

  getRoles: async () => {
      const url = `${API_BASE_URL}/enum/roles`;
      return await apiRequest(url, { method: "GET" });
  },

    create: async ( data, token) => {
      const url = `${API_BASE_URL}/user/signup`;
      return await apiRequest(url, {
          method: "POST",
          headers: {
              "Content-Type": "application/json",
                Authorization: `Bearer ${token}`,
          },
          body: JSON.stringify(data),
      },
          token);
    },

    finaliser: async (userId, data, token) => {
      const url = `${API_BASE_URL}/user/${userId}/finaliser`;
      return await apiRequest(url, {
          method: "PATCH",
          headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${token}`,
          },
          body: JSON.stringify(data),
      },
          token);
    }
};
