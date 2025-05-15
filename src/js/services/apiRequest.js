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

    if (!response.ok) {
      throw new Error(`Erreur HTTP ${response.status}`);
    }

    if (response.status === 204) return null;

    const contentType = response.headers.get("content-type");
    if (contentType && contentType.includes("application/json")) {
      return await response.json();
    } else {
      return await response.text();
    }
  } catch (err) {
    console.error("API request failed:", err);
    throw err;
  }
};
