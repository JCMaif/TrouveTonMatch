import { useRef } from "react";
import { FaEdit } from "react-icons/fa";

const EditableField = ({ label, field, value, editedFields, handleFieldChange }) => {
    const inputRef = useRef(null);

    const handleEditClick = () => {
        handleFieldChange(field, value); // Active l'édition
        setTimeout(() => inputRef.current?.focus(), 0); // Met le focus sur l'input
    };

    return (
        <div className="editable-field">
            <label><strong>{label} : </strong></label>

            {editedFields[field] !== undefined ? (
                <input
                    ref={inputRef}
                    type="text"
                    value={editedFields[field]}
                    onChange={(e) => handleFieldChange(field, e.target.value)}
                />
            ) : (
                <span onClick={handleEditClick} className="editable-text">
                    {value || "Non renseigné"}
                </span>
            )}

            <FaEdit
                className="edit-icon"
                style={{ marginLeft: '8px', cursor: 'pointer', color: "#e31766" }}
                onClick={handleEditClick}
                aria-label={`Modifier ${label}`}
                tabIndex="0"
                role="button"
            />
        </div>
    );
};

export { EditableField };
