import React, {useRef, useEffect, useState} from "react";
import { useNavigate } from "react-router-dom";
import "./Header.scss";
import Nav from "../../components/Nav/Nav";
import Logout from "../../components/Logout/Logout";

const Header = () => {
    const menuRef = useRef(null);
    const buttonRef = useRef(null);
    const [menuOpen, setMenuOpen] = useState(false);
    const navigate = useNavigate();
    const navigateToHome = () => navigate("/");

    useEffect(() => {
        const handleClickOutside = (event) => {
            if (
                menuRef.current &&
                !menuRef.current.contains(event.target) &&
                buttonRef.current &&
                !buttonRef.current.contains(event.target)
            ) {
                setMenuOpen(false);
            }
        };



        document.addEventListener("mousedown", handleClickOutside);

        return () => {
            document.removeEventListener("mousedown", handleClickOutside);
        };
    }, []);

    return (
        <header className="header">
            <div className="logo" title="Initiative Deux-Sèvres" onClick={navigateToHome}>
                <img src="/logo.png" alt="logo"/>
            </div>

            <button
                ref={buttonRef}
                className="menu-button"
                aria-label="Ouvrir le menu"
                aria-expanded={menuOpen}
                aria-controls="nav-menu"
                onClick={() => setMenuOpen((prev) => !prev)}
            >
                &#9776;
            </button>

            <nav
                className={`nav ${menuOpen ? "open" : ""}`}
                ref={menuRef}
                id="nav-menu"
                role="navigation"
                aria-label="Menu principal"
            >
                <ul>
                    <Nav/>
                </ul>
            </nav>

            <Logout/>

        </header>
    );
};

export default Header;
