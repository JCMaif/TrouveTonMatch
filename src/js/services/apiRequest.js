export const apiRequest = async (endpoint, options = {}, token = null) => {
  const headers = {
    ...options.headers,
    "Content-Type": "application/json",
  };

  if (token) {
    headers["Authorization"] = `Bearer ${token}`;
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
