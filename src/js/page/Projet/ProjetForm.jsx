import { useContext, useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { useAuthenticatedService } from "../../hook/useAuthenticatedService.js";
import { projetService, userService } from "../../services/services.js";
import InputField from "../../components/common/InputField/InputField.jsx";
import EditButton from "../../components/common/buttons/EditButton/EditButton.jsx";
import { AuthContext } from "@/context/AuthContext.jsx";

const ProjetForm = ({ isEditing }) => {
    const { projetId } = useParams();
    const { isAuthenticated } = useContext(AuthContext);
    const { findById, create, updateProjet } = useAuthenticatedService(projetService);
    const navigate = useNavigate();

    const [projet, setProjet] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [isSubmitting, setIsSubmitting] = useState(false);

    const [parrainsDisponibles, setParrainsDisponibles] = useState([]);
    const [parrainId, setParrainId] = useState("");


    const initialState = {
        porteurId: isAuthenticated.id,
        title: "",
        description: "",
        startingDate: ""
    };

    const [projetState, setProjetState] = useState(initialState);

    const userCanEdit = isAuthenticated && (
        isAuthenticated.id === projet?.porteur?.id ||
        isAuthenticated.role === "ADMIN" ||
        isAuthenticated.role === "STAFF"
    );

    console.log("can edit ? ", userCanEdit);
    console.log("projet :", projet);

    useEffect(() => {
        if (projetId) {
            const fetchProjet = async () => {
                try {
                    const data = await findById(projetId);
                    console.log("data du projet : ", data)
                    if (!data) {
                        navigate("/creer-projet");
                    } else {
                        setProjet(data);
                        setProjetState({
                            title: data.title || "",
                            description: data.description || "",
                            startingDate: data.startingDate || "",
                        });
                    }
                } catch (e) {
                    setError("Échec de chargement du projet.");
                    console.error("Erreur :", e);
                } finally {
                    setLoading(false);
                }
            };
            fetchProjet();
        } else {
            setLoading(false);
            navigate("/creer-projet");
        }
    }, [projetId, navigate]);

    useEffect(() => {
        const fetchParrains = async () => {
            try {
                const parrains = await userService.findParrainsDisponibles(isAuthenticated.token);
                setParrainsDisponibles(parrains);
                console.log("parrains disponibles : ", parrains);
            } catch (e) {
                console.error("Erreur chargement parrains :", e);
            }
        };

        if (isEditing && userCanEdit && !projet?.parrain) {
            fetchParrains();
        }
    }, [isEditing, userCanEdit, projet]);


    useEffect(() => {
        if (!projetId) {
            setProjetState(initialState);
        }
    }, [projetId]);

    const handleChange = (name, value) => {
        setProjetState(prevState => ({
            ...prevState,
            [name]: value,
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setIsSubmitting(true);
        try {
            if (isEditing) {
                await updateProjet(projetId, projetState);
            } else {
                await create(projetState);
            }
            alert(isEditing ? "Projet mis à jour !" : "Projet créé avec succès !");
            navigate(isEditing ? `/projet/${projetId}` : "/projets");
        } catch (error) {
            console.error("Erreur :", error.response?.data || error.message);
            alert("Une erreur est survenue.");
        } finally {
            setIsSubmitting(false);
        }
    };

    const handleAbort = () => {
        setProjetState(initialState);
        navigate("/projets");
    };

    const handleParrainChange = async (parrainId) => {
        try {
            const updated = await projetService.affecterParrain(projetId, parrainId, isAuthenticated.token);
            setProjet(updated);
        } catch (err) {
            console.error("Erreur attribution parrain :", err);
        }
    };


    if (loading) return <p>Chargement...</p>;
    if (error) return <p className="error-message">{error}</p>;

    return (
        <div className="container">
            {isEditing || !projetId ? (
                <>
                    <h2>{isEditing ? "Modifier le projet" : "Créer un projet"}</h2>
                    <form onSubmit={handleSubmit}>
                        <InputField
                            type="text"
                            placeholder="Titre du projet"
                            name="title"
                            value={projetState.title}
                            onChange={(value) => handleChange("title", value)}
                            mandatory={!isEditing}
                        />
                        <textarea
                            placeholder="Description du projet"
                            name="description"
                            value={projetState.description}
                            onChange={(e) => handleChange("description", e.target.value)}
                            required={!isEditing}
                        />
                        <InputField
                            type="date"
                            placeholder="Date de lancement"
                            name="startingDate"
                            value={projetState.startingDate}
                            onChange={(value) => handleChange("startingDate", value)}
                            mandatory={!isEditing}
                        />
                        {isEditing && userCanEdit  && (
                            <div className="form-group">
                                <label htmlFor="parrain">Affecter un parrain :</label>
                                <select
                                    id="parrain"
                                    name="parrain"
                                    value={parrainId}
                                    onChange={(e) => setParrainId(e.target.value)}
                                >
                                    <option value="">-- Sélectionner un parrain --</option>
                                    {parrainsDisponibles.map(parrain => (
                                        <option key={parrain.id} value={parrain.id}>
                                            {parrain.firstName} {parrain.lastName}
                                        </option>
                                    ))}
                                </select>
                                <button
                                    type="button"
                                    onClick={() => handleParrainChange(parrainId)}
                                    disabled={!parrainId}
                                >
                                    Affecter le parrain
                                </button>
                            </div>
                        )}

                        <button type="submit" disabled={isSubmitting}>
                            {isSubmitting ? "Enregistrement..." : isEditing ? "Modifier" : "Créer"}
                        </button>
                        <button type="reset" onClick={handleAbort}>Annuler</button>
                    </form>
                </>
            ) : (
                <>
                    <div className="title-container">
                        <h1>{projet?.title || "Projet sans titre"}</h1>
                        {userCanEdit && (
                            <EditButton onClick={() => navigate(`/projet/edit/${projetId}`)}>Modifier</EditButton>
                        )}
                    </div>
                    <p><strong>Porteur :</strong> {projet?.porteur?.firstname} {projet?.porteur?.lastname}</p>
                    <p><strong>Date de lancement :</strong> {projet?.startingDate ? new Date(projet.startingDate).toLocaleDateString() : "Non renseignée"}</p>
                    <p><strong>Description :</strong> {projet?.description || "Aucune description"}</p>
                    <p><strong>Parrain :</strong> {projet?.parrain ? `${projet.parrain.firstName} ${projet.parrain.lastName}` : "Choisissez un parrain"}</p>
                </>
            )}
        </div>
    );
};

export default ProjetForm;