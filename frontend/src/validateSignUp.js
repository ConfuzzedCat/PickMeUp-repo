export function validateSignUp(firstname, lastname, email, password) {
    let errors = {};
    let isValid = true;

    if (/\d/.test(firstname)) {
        isValid = false;
        errors.firstname = "Names cannot contain numbers.";
    }

    if (/\d/.test(lastname)) {
        isValid = false;
        errors.lastname = "Names cannot contain numbers.";
    }

    if (!email.includes('@')) {
        isValid = false;
        errors.email = "Please enter a valid email address.";
    }

    if (password.length < 6) {
        isValid = false;
        errors.password = "Password must be at least 6 characters.";
    }

    return {
        isValid,
        errors
    };
}
