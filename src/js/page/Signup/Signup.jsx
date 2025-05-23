import React, { useContext, useState, useEffect } from "react";
import InputField from "../../components/common/InputField/InputField.jsx";
import { userService } from "../../services/services.js";
import { useAuthenticatedService } from "../../hook/useAuthenticatedService.js";
import { AuthContext } from "../../context/AuthContext.jsx";

const Signup = ({ role }) => {
    const initialState = {
        username: "",
        password: "test",
        email: "",
        role: role,
        plateforme: null,
        firstname: "",
        lastname: "",
    };

    const { isAuthenticated } = useContext(AuthContext);
    const { create } = useAuthenticatedService(userService);
    const [state, setState] = useState(initialState);
    const [isSubmitting, setIsSubmitting] = useState(false);

    useEffect(() => {
        if (isAuthenticated.role === "STAFF") {
            setState(prevState => ({
                ...prevState,
                plateforme: {id:isAuthenticated.plateformeId}
            }));
        }
    }, [isAuthenticated]);

    const handleChange = (name, value) => {
        setState(prevState => ({
            ...prevState,
            [name]: value
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!state.username || !state.email) {
            alert("Veuillez remplir tous les champs.");
            return;
        }

        setIsSubmitting(true);

        try {
            await create({
                username: state.username,
                email: state.email,
                password: state.password,
                role: state.role,
                enabled: false,
                plateforme: state.plateforme,
                firstname: state.firstname,
                lastname: state.lastname
            });
            alert("Utilisateur créé avec succès !");
            setState(initialState);
        } catch (error) {
            console.error("Erreur lors de la création:", error.response?.data || error.message);
            alert("Une erreur est survenue.");
        } finally {
            setIsSubmitting(false);
        }
    };

    return (
        <div className="container">
            <h2>Création d&#39;un {role}</h2>
            <form onSubmit={handleSubmit}>
                <InputField
                    type="text"
                    id="username"
                    placeholder="Nom d'utilisateur"
                    value={state.username}
                    onChange={(value) => handleChange("username", value)}
                    name="username"
                    mandatory
                />
                <InputField
                    type="email"
                    id="email"
                    placeholder="Email"
                    value={state.email}
                    onChange={(value) => handleChange("email", value)}
                    name="email"
                    mandatory
                />
                <InputField
                    type="text"
                    id="firstname"
                    placeholder="Prénom"
                    value={state.firstname}
                    onChange={(value) => handleChange("firstname", value)}
                    name="firstname"
                    mandatory
                />
                <InputField
                    type="text"
                    id="lastname"
                    placeholder="Nom de famille"
                    value={state.lastname}
                    onChange={(value) => handleChange("lastname", value)}
                    name="lastname"
                    mandatory
                />
                <button type="reset" onClick={() => setState(initialState)}>Annuler</button>
                <button type="submit" disabled={isSubmitting}>
                    {isSubmitting ? "Création..." : "Créer"}
                </button>

                <pre className="styleguide-state-preview">{JSON.stringify(state, null, 2)}</pre>
            </form>
        </div>
    );
};

export default Signup;