import { useState } from "react";
import { Dialog, DialogContent, DialogTrigger } from "../common/Dialog/Dialog.jsx";
import  Button from "../common/buttons/Button/Button.jsx";
import  Input from "../common/Input/Input.jsx";
import { Upload } from "lucide-react";
import "./Avatar.css";

const Avatar = ({ src, size = "petit", onUpdate }) => {
    const [image, setImage] = useState(src);
    const [newImage, setNewImage] = useState("");
    const isLarge = size === "grand";

    const handleImageChange = (e) => {
        const file = e.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = () => {
                setNewImage(reader.result);
            };
            reader.readAsDataURL(file);
        }
    };

    const handleSubmit = () => {
        if (newImage) {
            setImage(newImage);
            onUpdate(newImage);
        }
    };

    return (
        <Dialog>
            <DialogTrigger asChild>
                <div className={`avatar-container ${isLarge ? "large" : "small"}`}>
                    <img src={image} alt="User avatar" className="avatar-image" />
                </div>
            </DialogTrigger>
            <DialogContent>
                <div className="avatar-modal">
                    <h2 className="avatar-title">Changer la photo de profil</h2>
                    <Input
                        type="text"
                        placeholder="Entrer une URL"
                        value={newImage}
                        onChange={(e) => setNewImage(e.target.value)}
                    />
                    <div className="avatar-upload">
                        <Input type="file" accept="image/*" onChange={handleImageChange} />
                        <Upload className="upload-icon" />
                    </div>
                    <Button onClick={handleSubmit}>Mettre à jour</Button>
                </div>
            </DialogContent>
        </Dialog>
    );
};

export default Avatar;
