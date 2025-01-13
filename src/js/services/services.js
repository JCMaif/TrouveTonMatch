import { createService } from "./apiServiceFactory";
import { apiRequest } from "./apiRequest";
import { API_BASE_URL } from "../config/config";

export const plateformeService = createService("plateforme");
export const projetService = createService("projet");

export const userService = {
  ...createService("user"),
  getUsersByRole: async (role, token) => {
    const url = `${API_BASE_URL}/user/by-role?role=${role}`;
    return await apiRequest(url, { method: "GET" }, token);
  },
};
