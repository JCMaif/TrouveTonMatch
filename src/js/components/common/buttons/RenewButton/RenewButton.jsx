import React from 'react';
import { FaRedo } from 'react-icons/fa';
import './RenewButton.scss';

const RenewButton = ({ onClick }) => {
  return (
    <button className="renew-button" onClick={onClick}>
      <FaRedo />
    </button>
  );
};

export default RenewButton;
