import {apiRequest} from './apiRequest';

const BASE_URL ='http://localhost:8080/api';
const FETCH_USERS_URL = `${BASE_URL}/user`;
console.log(FETCH_USERS_URL);

export const userService = {
  getUsers: async () => {
    try {
      return await apiRequest(FETCH_USERS_URL, {method: 'GET'});
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
