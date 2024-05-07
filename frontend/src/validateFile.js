export function validateFile(file) {
    let errors = {};
    let isValid = true;

    if (!file) {
        isValid = false;
        errors.file = "Please upload a file.";
    } else {
        const allowedExtensions = /(\.jpg|\.jpeg|\.png)$/i;
        if (!allowedExtensions.exec(file.name)) {
            isValid = false;
            errors.file = "File type not allowed. Only images are allowed.";
        }
    }

    return {
        isValid,
        errors
    };
}
