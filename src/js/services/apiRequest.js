export const apiRequest = async (endpoint, options = {}, token = null, responseType = "json") => {
  const headers = { ...options.headers };

  if (token) {
    headers["Authorization"] = `Bearer ${token}`;
  }

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

    switch (responseType) {
      case "blob":
        return await response.blob();
      case "text":
        return await response.text();
      case "json":
      default:
        const contentType = response.headers.get("content-type");
        if (contentType && contentType.includes("application/json")) {
          return await response.json();
        } else {
          return await response.text();
        }
    }
  } catch (err) {
    console.error("API request failed:", err);
    throw err;
  }
};
