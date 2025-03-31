import React from "react";
import './ToggleButton.scss'

const ToggleButton = ({ isActive, onChange }) => {
    return (
        <label className="toggle-switch">
            <input
                type="checkbox"
                checked={isActive}
                onChange={(e) => onChange(e.target.checked)}
            />
            <span className="slider"></span>
        </label>
    );
};

export default ToggleButton;
