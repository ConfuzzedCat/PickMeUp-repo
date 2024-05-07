import React, { useState, useRef } from "react";
import { NavLink } from "react-router-dom";
import { validateSignUp } from "./validateSignUp";

// import facade from './util/apiFacade';
// import './css/App.css';


function Signup() {
  const init = { firstName: "", lastName: "", email: "", password: "", confirmPassword: "", file: null };
  const [signupData, setSignupData] = useState(init);
  const [error, setError] = useState("");

  const performSignup = async (evt) => {
      evt.preventDefault();

      // Validate passwords match
      if (signupData.password !== signupData.confirmPassword) {
          setError("Passwords do not match");
          return;
      }

      // Validate other fields
      const { isValid, errors } = validateSignUp(signupData.firstName, signupData.lastName, signupData.email, signupData.password, signupData.file);
      if (!isValid) {
          setError(Object.values(errors).join(", "));
          return;
      }

      try {
          // Simulate API call
          console.log("Signup successful", signupData);
          // Reset form or redirect user
          setSignupData(init);
          setError("");
      } catch (error) {
          setError("Error during signup. Please try again.");
          console.error(error);
      }
  };

  const onChange = (evt) => {
      if (evt.target.id === "file") {
          setSignupData({
              ...signupData,
              file: evt.target.files[0],
          });
      } else {
          setSignupData({
              ...signupData,
              [evt.target.id]: evt.target.value,
          });
      }
  };

  return (
      <>
          <div className="box">
              <h1>Signup</h1>
              <form onSubmit={performSignup}>
                  <input placeholder="First name" id="firstName" value={signupData.firstName} onChange={onChange} />
                  <input placeholder="Last name" id="lastName" value={signupData.lastName} onChange={onChange} />
                  <input placeholder="Email" id="email" value={signupData.email} onChange={onChange} />
                  <input placeholder="Password" type="password" id="password" value={signupData.password} onChange={onChange} />
                  <input placeholder="Confirm Password" type="password" id="confirmPassword" value={signupData.confirmPassword} onChange={onChange} />
                  <input type="file" id="file" onChange={onChange} />
                  <button type="submit">Sign up</button>
                  <NavLink to="/loginpage"><button type="button">Login</button></NavLink>
              </form>
              {error && <p className="error-message">{error}</p>}
          </div>
      </>
  );
}

export default Signup;