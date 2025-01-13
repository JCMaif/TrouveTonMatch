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

    if (!response.ok) {
      const errorData =
        response.headers.get("content-type")?.includes("application/json")
          ? await response.json()
          : await response.text();
      console.error("API error:", errorData);
      throw new Error(`HTTP Error ${response.status}: ${errorData}`);
    }

    if (response.status === 204) return null;

    if (response.headers.get("content-type")?.includes("application/json")) {
      return await response.json();
    }

    throw new Error("Unexpected response content type");
  } catch (err) {
    console.error("API request failed:", err);
    throw err;
  }
};
