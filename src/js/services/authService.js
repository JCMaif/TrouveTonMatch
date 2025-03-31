import {apiRequest} from "./apiRequest";
import { API_BASE_URL } from "../config/config";


export const authService = {
    login: async (username, password) => {
      const url = `${API_BASE_URL}/auth/login`;
      const options = {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, password }),
      };
        return await apiRequest(url, options);
    },

    signup: async (signupData) => {
      const url = `${API_BASE_URL}/auth/signup`;
      const options = {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(signupData),
      };
      await apiRequest(url, options); 
    },
  
    logout: async () => {
      const url = `${API_BASE_URL}/auth/logout`;
      const options = { method: "POST" };
      await apiRequest(url, options);
    },
  };
  