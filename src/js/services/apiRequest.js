export const apiRequest = async (url, options = {}) => {
  try {
    const response = await fetch(url, options);
    console.log("url: ", url);  
    console.log("Response status:", response.status);
    console.log("Response headers:", response.headers);
    console.log("Response body:", response.body);

    if (!response.ok) {
      const errorContentType = response.headers.get("content-type");
      const errorData = errorContentType && errorContentType.includes("application/json")
        ? await response.json()
        : await response.text();

      console.error("Error response body:", errorData);
      throw new Error(`HTTP Error ${response.status}: ${errorData}`);
    }

    if (response.status === 204 || response.headers.get("content-length") === "0") {
      console.log("Response with no content.");
      return null; // Pas de contenu
    }

    const contentType = response.headers.get("content-type");


    if (contentType && contentType.includes("application/json")) {
      try {
        const jsonResponse = await response.json();
        return jsonResponse;
      } catch (error) {
        console.error("Failed to parse JSON response:", error);
        throw new Error("Invalid JSON response");
      }
    }

    console.error("Unexpected content type:", contentType);
    throw new Error("Expected JSON response but received different content type");
  } catch (err) {
    console.error("API request error:", err);
    throw err;
  }
};
