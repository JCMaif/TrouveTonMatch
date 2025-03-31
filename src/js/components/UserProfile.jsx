
import React from "react";
import { FaRegUser } from "react-icons/fa";
import { API_BASE_URL } from "../config/config";
import RenewButton from "../components/common/buttons/RenewButton/RenewButton.jsx";

export const UserProfile = ({ userDetails, handleUserClick, showRenewButton, handleRenewActivationCode }) => {
    return (
        <div className="profile-picture-wrapper-petit">
            <div className="profile-picture-container-petit">
                {userDetails?.profilePicture ? (
                    <img
                        className="pictureProfilePetit"
                        src={`${API_BASE_URL}/uploads/${userDetails.profilePicture}`}
                        alt="Avatar utilisateur"
                    />
                ) : (
                    <FaRegUser size={50} color="#ccc" />
                )}
            </div>
            <div onClick={() => handleUserClick(userDetails.id)}>
                {userDetails.firstName} {userDetails.lastName}
            </div>
            {showRenewButton && !userDetails.enabled && (
                <RenewButton onClick={() => handleRenewActivationCode(userDetails.id)} />
            )}
        </div>
    );
};
