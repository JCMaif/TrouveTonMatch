import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useAuthenticatedService } from "../../hook/useAuthenticatedService.js";
import { compteRenduService } from "../../services/services";

const CompteRenduForm = ({isEditing}) => {
    const {findById, update} = useAuthenticatedService(compteRenduService);
    const {isAuthenticated} = useContext(AuthContext);
    const navigate = useNavigate();
    const [compteRendu, setCompteRendu] = useState({});
    const [isSubmitting, setIsSubmitting] = useState(false);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(true);
    const userCanEdit = isAuthenticated && (
        isAuthenticated.id === projet?.porteur?.id ||
        isAuthenticated.role === "ADMIN" ||
        isAuthenticated.role === "STAFF"
    );
    const [isEditing, setIsEditing] = useState(false);
};
export default CompteRenduForm;