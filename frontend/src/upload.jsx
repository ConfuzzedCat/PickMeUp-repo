import React, { useState } from 'react';
import { validateFile } from './validateFile'; 

function Upload() {
    const [file, setFile] = useState(null);
    const [error, setError] = useState('');

    const handleFileChange = (event) => {
        const selectedFile = event.target.files[0];
        if (selectedFile) {
            const { isValid, errors } = validateFile(selectedFile);
            if (!isValid) {
                setError(errors.file);
                setFile(null);  
            } else {
                setFile(selectedFile);
                setError('');  
            }
        }
    };

    const uploadFile = async () => {
        if (!file) {
            alert("Please select a valid file first.");
            return;
        }
        console.log("Uploading file:", file);
        
    };

    return (
        <div>
            <h1>Upload File</h1>
            <input type="file" onChange={handleFileChange} />
            {error && <p className="error">{error}</p>}
            <button onClick={uploadFile} disabled={!file}>Upload</button>
        </div>
    );
}

export default Upload;
