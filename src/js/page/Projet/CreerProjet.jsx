import React, {useContext, useState} from 'react';
import InputField from "../../components/common/InputField/InputField.jsx";
import useValidatedState from "../../hook/use-validated-state.js";
import { projetService } from "../../services/services.js";
import {useAuthenticatedService} from "../../hook/useAuthenticatedService.js";
import { AuthContext} from "../../context/AuthContext.jsx";

const CreerProjet = () => {

    const { isAuthenticated } = useContext(AuthContext);

    const initialState = {
        porteurId : isAuthenticated.id,
        title : undefined,
        description : undefined,
        startingDate: undefined
    };
    const { state, errors, setState, validate } = useValidatedState(initialState);
    const [isSubmitting, setIsSubmitting] = useState(false);
    const { create } = useAuthenticatedService(projetService);

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!state.title || !state.description || !state.startingDate ) {
            alert("Veuillez remplir tous les champs.");
            return;
        }
        setIsSubmitting(true);
        try {
            await create({
                porteurId: state.porteurId,
                title: state.title,
                description: state.description,
                startingDate: state.startingDate,
            });
            alert("Projet créé avec succès !");
            setState(initialState);
        } catch (error) {
            console.error("Erreur lors de la création:", error.response?.data || error.message);
            alert("Une erreur est survenue.");
        } finally {
            setIsSubmitting(false);
        }
    }

    return (
        <div className="container">
            <h2>Page de création d'un projet</h2>
            <form onSubmit={handleSubmit}>
                <InputField
                    type="text"
                    placeholder="Titre du projet"
                    value={state.title}
                    onChange={(title) => setState({...state, title})}
                    name="title"
                    mandatory
                />
                <InputField
                    type="text"
                    placeholder="Description du projet"
                    value={state.description}
                    onChange={(description) => setState({...state, description})}
                    name="description"
                    mandatory
                />
                <p>Date de lancement</p>
                <InputField
                    type="date"
                    placeholder="Date de lancement"
                    value={state.startingDate}
                    onChange={(startingDate) => setState({...state, startingDate})}
                    name="startingDate"
                    mandatory
                />
                <button type="reset" onClick={() => setState(initialState)}>Annuler</button>
                <button type="submit" disabled={isSubmitting}>
                    {isSubmitting ? "Création..." : "Créer"}
                </button>

                <pre className="styleguide-state-preview">{JSON.stringify({state}, null, 2)}</pre>
            </form>


        </div>
    );
};

export default CreerProjet;