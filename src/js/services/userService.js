import { apiRequest } from './apiRequest';

const BASE_URL =import.meta.env.VITE_APP_BASE_URL;
const FETCH_USERS_URL = `${BASE_URL}/user`;
console.log(FETCH_USERS_URL);

export const userService = {
  getUsers: async () => {
    try {
      const data = await apiRequest(FETCH_USERS_URL, { method: 'GET' });
      
      return data; 
    } catch (err) {
      throw new Error('Failed to fetch users');
    }
  },

  deleteUser: async (userId) => {
    try {
      await apiRequest(`${FETCH_USERS_URL}/${userId}`, { method: 'DELETE' });
    } catch (err) {
      throw new Error('Failed to delete user');
    }
  }
};
