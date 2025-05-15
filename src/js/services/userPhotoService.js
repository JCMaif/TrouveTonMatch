import { apiRequest } from "./apiRequest";
import { API_BASE_URL } from "../config/config";

export const userPhotoService = {
    uploadProfileImage,
};

async function uploadProfileImage(userId, file, token) {
    const formData = new FormData();
    formData.append("file", file);

    const response = await apiRequest(
        `${API_BASE_URL}/uploads/profiles/${userId}`,
        {
            method: "POST",
            body: formData,
        },
        token
    );

    return response;
}
