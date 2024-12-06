import React from 'react';
import './InputField.css';

const sanitizeInput = (input) => {
  const element = document.createElement("div");
  if (input) {
    element.innerText = input;
    return element.innerHTML; 
  }
  return input;
};

const InputField = ({ type, placeholder, value, onChange, name, id, pattern, mandatory }) => {
  const handleChange = (e) => {
    const sanitizedValue = sanitizeInput(e.target.value);
    onChange(sanitizedValue); 
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
      required={mandatory ? true : false}
    />
  );
};

export default InputField;
