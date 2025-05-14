import React from "react";
import "./InputField.css";

const sanitizeInput = (input) => {
  return input.replace(/[<>]/g, ""); // Supprime les caractères dangereux
};

const InputField = ({ type, placeholder, value, onChange, name, id, pattern, mandatory }) => {
  const handleChange = (e) => {
    const sanitizedValue = sanitizeInput(e.target.value);

    if (onChange.length === 2) {
      onChange(name, sanitizedValue);
    } else {
      onChange(sanitizedValue);
    }
  };

  return (
      <input
          type={type}
          placeholder={placeholder}
          value={value}
          onChange={handleChange}
          name={name}
          id={id}
          pattern={pattern}
          required={!!mandatory}
      />
  );
};

export default InputField;