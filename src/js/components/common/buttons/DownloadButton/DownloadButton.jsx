import React from 'react';
import {FaCloudDownloadAlt} from 'react-icons/fa';
import './DownloadButton.scss';

const DownloadButton = ({ onClick }) => {
  return (
    <button className="download-button" onClick={onClick}>
      <FaCloudDownloadAlt />
    </button>
  );
};

export default DownloadButton;
