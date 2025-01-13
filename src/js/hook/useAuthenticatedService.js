import { useContext }    from "react";
import { AuthContext }   from "../context/AuthContext";

export const useAuthenticatedService = (service) => {
    const { isAuthenticated } = useContext(AuthContext);

    if (!isAuthenticated) {
        throw new Error("Utilisateur non authentifié");
    }

    const token = isAuthenticated.token;

    return Object.keys(service).reduce((wrappedService, methodName) => {
        wrappedService[methodName] = async (...args) => {
            return await service[methodName](...args, token);
        };
        return wrappedService;
    }, {});
}