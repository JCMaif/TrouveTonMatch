import React from "react";
import { FaEdit } from "react-icons/fa";

const EditableField = ({ label, field, value, editedFields, handleFieldChange }) => (
    <div className="editable-field">
        <FaEdit className="edit-icon" style={{ marginRight: '8px', cursor: 'pointer' }} />
        <label><strong>{label} : </strong></label>
        {editedFields[field] !== undefined ? (
            <input
                type="text"
                value={editedFields[field]}
                onChange={(e) => handleFieldChange(field, e.target.value)}
            />
        ) : (
            <span onClick={() => handleFieldChange(field, value)}>{value || "Non renseigné"}</span>
        )}
    </div>
);

export { EditableField };
