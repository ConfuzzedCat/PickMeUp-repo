export function validateSignUp(firstname, lastname, email, password, file) {
  let errors = {};
  let isValid = true;

  if (/\d/.test(firstname)) {
    // /\d/ means it cannot contain numbers.
    isValid = false;
    errors.firstname = "Names cannot contain numbers.";
  }

  if (/\d/.test(lastname)) {
    // /\d/ means it cannot contain numbers.
    isValid = false;
    errors.lastname = "Names cannot contain numbers.";
  }

  if (!email.includes("@")) {
    isValid = false;
    errors.email = "Please enter a valid email address.";
  }

  if (password.length < 6) {
    isValid = false;
    errors.password = "Password must be at least 6 characters.";
  }

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
    errors,
  };
}
