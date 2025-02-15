import React, { useState } from "react";
import "./Dialog.css";

export const Dialog = ({ children, isOpen, onClose }) => {
    if (!isOpen) return null;
    return (
        <div className="dialog-overlay" onClick={onClose}>
            <div className="dialog-content" onClick={(e) => e.stopPropagation()}>
                {children}
            </div>
        </div>
    );
};

export const DialogTrigger = ({ children, onOpen }) => (
    <div onClick={onOpen} className="dialog-trigger">
        {children}
    </div>
);

export const DialogContent = ({ children }) => <div>{children}</div>;
