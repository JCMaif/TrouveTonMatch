import React from "react";
import "./Combobox.scss";

const Combobox = ({ id, value, label, options, onChange }) => {
    return (
        <div className="combobox">
            <select
                id={id}
                value={value}
                onChange={(e) => onChange(e.target.value)}

            >
                <option value="" disabled>Sélectionnez :</option>
                {options.map((option) => (
                    <option key={option} value={option}>
                        {option}
                    </option>
                ))}
            </select>
        </div>
    );
};

export default Combobox;
