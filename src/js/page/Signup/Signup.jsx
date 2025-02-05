import React, { useEffect, useState } from "react";
import InputField from "../../components/common/InputField/InputField.jsx";
import useValidatedState from "../../hook/use-validated-state.js";
import { useAuthenticatedService } from "../../hook/useAuthenticatedService.js";
import { userService } from "../../services/services.js";
import Combobox from "../../components/common/Combobox/Combobox.jsx";

const Signup = () => {
    const initialState = {
        username: "",
        password: "test",
        email: "",
        role: "",
    };

    const { state, errors, setState, validate } = useValidatedState(initialState);
    const [roleOptions, setRoleOptions] = useState([]);
    const { getRoles, create } = useAuthenticatedService(userService);
    const [isSubmitting, setIsSubmitting] = useState(false);


    useEffect(() => {
        const fetchRoles = async () => {
            try {
                const data = await getRoles();
                setRoleOptions(data);
            } catch (err) {
                console.error("Failed to fetch roles:", err.response?.data || err.message);
            }
        };
        fetchRoles();
    }, []);

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!state.username || !state.email || !state.role ) {
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
            <h2>Création d'utilisateur</h2>
            <form onSubmit={handleSubmit}>
                <InputField
                    type="text"
                    id="username"
                    placeholder="Nom d'utilisateur"
                    value={state.username}
                    onChange={(username) => setState({ ...state, username })}
                    name="username"
                    mandatory
                />
                <InputField
                    type="email"
                    id="email"
                    placeholder="Email"
                    value={state.email}
                    onChange={(email) => setState({ ...state, email })}
                    name="email"
                    mandatory
                />
                <Combobox
                    value={state.role}
                    id="role"
                    label="Rôle"
                    options={roleOptions}
                    onChange={(role) => setState({ ...state, role })}
                />

                <button type="reset" onClick={() => setState(initialState)}>Annuler</button>
                <button type="submit" disabled={isSubmitting}>
                    {isSubmitting ? "Création..." : "Créer"}
                </button>

                <pre className="styleguide-state-preview">{JSON.stringify({ state }, null, 2)}</pre>
            </form>
        </div>
    );
};

export default Signup;
