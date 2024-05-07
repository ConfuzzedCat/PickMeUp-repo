import React, { useState } from "react";
import { NavLink, useNavigate } from "react-router-dom";
import { validateSignUp } from "./validateSignUp";

function Signup() {
    const init = { firstName: "", lastName: "", email: "", password: "", confirmPassword: "" };
    const [signupData, setSignupData] = useState(init);
    const [error, setError] = useState("");
    const navigate = useNavigate();

    const performSignup = async (evt) => {
        evt.preventDefault();

        if (signupData.password !== signupData.confirmPassword) {
            setError("Passwords do not match");
            return;
        }

        const { isValid, errors } = validateSignUp(signupData.firstName, signupData.lastName, signupData.email, signupData.password);
        if (!isValid) {
            setError(Object.values(errors).join(", "));
            return;
        }

        try {
            console.log("Signup successful", signupData);
            // Assuming signup is successful, redirect to the upload page
            navigate('/upload');
        } catch (error) {
            setError("Error during signup. Please try again.");
            console.error(error);
        }
    };

    const onChange = (evt) => {
        setSignupData({
            ...signupData,
            [evt.target.id]: evt.target.value,
        });
    };

    return (

        

        
        <div className="box">
            <h1>Signup</h1>
            <form onSubmit={performSignup}>
                <input placeholder="First name" id="firstName" value={signupData.firstName} onChange={onChange} />
                <input placeholder="Last name" id="lastName" value={signupData.lastName} onChange={onChange} />
                <input placeholder="Email" id="email" value={signupData.email} onChange={onChange} />
                <input placeholder="Password" type="password" id="password" value={signupData.password} onChange={onChange} />
                <input placeholder="Confirm Password" type="password" id="confirmPassword" value={signupData.confirmPassword} onChange={onChange} />
                <button type="submit">Sign up</button>
                <NavLink to="/loginpage"><button type="button">Login</button></NavLink>
            </form>
            {error && <p className="error-message">{error}</p>}
        </div>
        
    );
}

export default Signup;
