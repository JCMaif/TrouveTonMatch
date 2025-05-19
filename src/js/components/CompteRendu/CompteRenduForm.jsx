import React, {useContext, useEffect, useState} from 'react';
import {enumService, compteRenduService} from "../../services/services.js";
import { AuthContext } from "../../context/AuthContext.jsx";


const CompteRenduForm = () => {
    const [MOYENS, setMOYENS] = useState([]);
    const [loadingEnums, setLoadingEnums] = useState(true);
    const { isAuthenticated } = useContext(AuthContext);

    const [form, setForm] = useState({
        dateEchange: '',
        heureEchange: '',
        moyenEchange: '',
        sujets: '',
        resume: '',
        actionsAMener: '',
        prochainRdv: '',
    });

    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');

    useEffect(() => {
        const fetchEnums = async () => {
            try {
                const moyens = await enumService.findByName("moyens");
                setMOYENS(moyens);
            } catch (err) {
                console.error("Erreur chargement moyens:", err);
                setError("Erreur lors du chargement des moyens d’échange.");
            } finally {
                setLoadingEnums(false);
            }
        };
        fetchEnums();
    }, []);

    const handleChange = (e) => {
        const {name, value} = e.target;
        setForm((prev) => ({...prev, [name]: value}));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');
        setSuccess('');
        setLoading(true);

        try {
            const data = {
                ...form,
                moyenEchange: form.moyenEchange || null,
                heureEchange: form.heureEchange || null,
                prochainRdv: form.prochainRdv || null,
            };
            await compteRenduService.create(data, isAuthenticated.token);

            setSuccess('Compte-rendu créé avec succès.');
            setForm({
                dateEchange: '',
                heureEchange: '',
                moyenEchange: '',
                sujets: '',
                resume: '',
                actionsAMener: '',
                prochainRdv: '',
            });
        } catch (err) {
            console.error(err);
            setError('Une erreur est survenue lors de la création du compte-rendu.');
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="container">
            <form onSubmit={handleSubmit}>
                <div>
                    <label htmlFor="dateEchange">Date de l’échange </label>
                    <input type="date" name="dateEchange" value={form.dateEchange} onChange={handleChange} required/>
                </div>

                <div>
                    <label htmlFor="heureEchange">Heure de l’échange</label>
                    <input type="time" name="heureEchange" value={form.heureEchange} onChange={handleChange}/>
                </div>

                <div>
                    <label htmlFor="moyenEchange">Moyen d’échange</label>
                    <select name="moyenEchange" value={form.moyenEchange} onChange={handleChange}>
                        <option value="">-- Sélectionner --</option>
                        {MOYENS.map((moyen) => (
                            <option key={moyen} value={moyen}>{moyen}</option>
                        ))}
                    </select>
                </div>

                <div>
                    <label htmlFor="sujets">Sujets abordés</label>
                    <textarea name="sujets" value={form.sujets} onChange={handleChange}/>
                </div>

                <div>
                    <label htmlFor="resume">Résumé</label>
                    <textarea name="resume" value={form.resume} onChange={handleChange}/>
                </div>

                <div>
                    <label htmlFor="actionsAMener">Actions à mener</label>
                    <textarea name="actionsAMener" value={form.actionsAMener} onChange={handleChange}/>
                </div>

                <div>
                    <label htmlFor="prochainRdv">Prochain RDV</label>
                    <input type="date" name="prochainRdv" value={form.prochainRdv} onChange={handleChange}/>
                </div>

                <div>
                    <button type="submit" disabled={loading}>
                        {loading ? 'Envoi en cours...' : 'Créer'}
                    </button>
                </div>

                {success && <p className="success-message">{success}</p>}
                {error && <p className="error-message">{error}</p>}
            </form>
        </div>
    );
};

export default CompteRenduForm;
