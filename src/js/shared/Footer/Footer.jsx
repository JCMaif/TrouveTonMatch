import React from "react";
import { FaLinkedin, FaInstagram, FaFacebook, FaTwitter } from "react-icons/fa";
import "./Footer.scss";

const Footer = () => {
  return (
    <footer className="footer">
      <div className="footer-contacts">
        <p>
          Téléphone : <a href="tel:+33679875609">06 79 87 56 09</a> | Email :{" "}
          <a href="mailto:accompagnement@initiativedeuxsevres.fr">accompagnement@initiativedeuxsevres.fr</a>
        </p>
      </div>
      <div className="footer-social">
        <a
          href="https://www.linkedin.com/company/initiative-deux-sevres/"
          target="_blank"
          rel="noopener noreferrer"
          className="social-icon"
        >
          <FaLinkedin />
        </a>
        <a
          href="https://www.instagram.com/inititiave_deux_sevres/"
          target="_blank"
          rel="noopener noreferrer"
          className="social-icon"
        >
          <FaInstagram />
        </a>
        <a
          href="https://www.facebook.com/profile.php?id=61556616180678"
          target="_blank"
          rel="noopener noreferrer"
          className="social-icon"
        >
          <FaFacebook />
        </a>
        <a
          href="https://twitter.com/InitiativeFR?t=FAlzGZRA3_T4lDpDse6z_w&amp;s=09"
          target="_blank"
          rel="noopener noreferrer"
          className="social-icon"
        >
          <FaTwitter />
        </a>
      </div>
      <div className="footer-copyright">
        <p>&copy; 2024 JCA. Tous droits réservés.</p>
      </div>
    </footer>
  );
};

export default Footer;
