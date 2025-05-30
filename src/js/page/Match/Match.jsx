import React, {useContext, useState} from "react";
import '../../styles/page.scss';
import {AuthContext} from "@/context/AuthContext.jsx";
import {useNavigate} from "react-router-dom";

const Match = () => {
    const [error, setError] = useState("");
    const { isAuthenticated } = useContext(AuthContext);
    const navigate = useNavigate();

    const handleUserClick = (id) => {
        navigate(`/match/${id}`);
    };

    return (
        <div className="container">
            <h1>Matches</h1>
            {error && <p className="error-message">{error}</p>}
            <table>
                <thead>
                <tr>
                    <th></th>
                </tr>
                </thead>
                {isAuthenticated && (
                    <tbody>
                    {/*{users.map((user) => (*/}
                    {/*    <tr key={user.id}>*/}
                    {/*        <td onClick={() => handleUserClick(user.id)}>*/}
                    {/*            {user.username}*/}
                    {/*        </td>*/}
                    {/*    </tr>*/}
                    {/*))}*/}
                    </tbody>
                )}
            </table>
        </div>
    );
};  

export default Match;