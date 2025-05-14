// eslint-disable-next-line no-unused-vars
import React from 'react';
import { FaEdit } from 'react-icons/fa'; 
import './EditButton.scss';

const EditButton = ({ onClick }) => {
  return (
    <button className="edit-button" onClick={onClick}>
      <FaEdit />
    </button>
  );
};

export default EditButton;
