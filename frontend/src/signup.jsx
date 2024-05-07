import React, { useState, useRef } from "react";
import { NavLink } from "react-router-dom";
// import facade from './util/apiFacade';
// import './css/App.css';


function Signup(){
    const init = { email: "", firstName: "", lastName: "", password: "", confirmPassword: "" };
    const [signupData, setSignupData] = useState(init);
    const [error, setError] = useState(false);
  
    const email = useRef(null);
    const firstName = useRef(null);
    const lastName = useRef(null);
    const password = useRef(null);
    const confirmPassword = useRef(null);
  
    const performSignup = async (evt) => {
      evt.preventDefault();
    
      // Validate passwords match
      if (signupData.password !== signupData.confirmPassword) {
        setError("Passwords do not match");
        return;
      }
    
      try {
        // Call the signup function from facade
        await facade.signup(signupData.username, signupData.password);
    
        // Assuming signup is successful, you may want to do something with the user data
        // For example, setting user data in the state
        setUser(signupData.username); 
    
  
      } catch (error) {
        // Handle signup error
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
      <>
        <div className="box">
          <h1>Signup</h1>
  
          <form onChange={onChange}>
            <input placeholder="First name" id="firstname" ref={firstName} />
            <input placeholder="Last name" id="lastname" ref={lastName} />
            <input placeholder="Email" id="email" ref={email} />
            <input placeholder="Password" type="password" id="password" ref={password} />
            <input placeholder="Confirm Password" type="password" id="confirmPassword" ref={confirmPassword} />
            <button onClick={performSignup}>Sign up</button>
            <NavLink to="/loginpage" activeClassName="active"><button className="btns">Login</button></NavLink>
          </form>
  
          {error && <p className="error-message">{error}</p>}
        </div>
      </>
    );
  
}

export default Signup;