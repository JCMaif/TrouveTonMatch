export const apiRequest = async (endpoint, options = {}, token = null) => {
  const headers = { ...options.headers };

  if (token) {
    headers["Authorization"] = `Bearer ${token}`;
  }

  // Ne pas définir le Content-Type si le corps est une instance de FormData
  if (!(options.body instanceof FormData)) {
    headers["Content-Type"] = "application/json";
  }

  const finalOptions = { ...options, headers };

  try {
    const response = await fetch(endpoint, finalOptions);
    return await response.json();
  } catch (err) {
    console.error("API request failed:", err);
    throw err;
  }
};
