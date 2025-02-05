import {apiRequest} from "./apiRequest";

const BASE_URL ='http://localhost:8080/api';

export const authService = {
    login: async (username, password) => {
      const url = `${BASE_URL}/auth/login`;
      const options = {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, password }),
      };
        return await apiRequest(url, options);
    },

    signup: async (signupData) => {
      const url = `${BASE_URL}/auth/signup`;
      const options = {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(signupData),
      };
      await apiRequest(url, options); 
    },
  
    logout: async () => {
      const url = `${BASE_URL}/auth/logout`;
      const options = { method: "POST" };
      await apiRequest(url, options);
    },
  };
  