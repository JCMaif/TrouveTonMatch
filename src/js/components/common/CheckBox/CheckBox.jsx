import React from 'react';
import './CheckBox.scss';

const Checkbox = ({ checked, onChange, label }) => {
  return (
    <div>
      <input
        type="checkbox"
        checked={checked}
        onChange={onChange}
      />
      <label>{label}</label>
    </div>
  );
};

export default Checkbox;
