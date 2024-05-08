import React, { useState, useRef } from "react";
import { NavLink, useNavigate } from "react-router-dom";
//import { validateSignUp } from "./validateSignUp";

function Signup() {
  const init = {
    email: "",
    password: "",
    address: "",
    fullname: "",
    licenseImage: null,
    StudentImage: null,
  };
  const [signupData, setSignupData] = useState(init);
  const [error, setError] = useState("");
  const navigate = useNavigate();


    const email = useRef(null);
    const password = useRef(null);
    const address = useRef(null);
    const fullname = useRef(null);
    const licenseImage = useRef(null);
    const StudentImage = useRef(null);
    


  const performSignup = async (evt) => {
    evt.preventDefault();
  
  
    const signupDataJson = JSON.stringify(signupData);
  
    localStorage.setItem('signupData', signupDataJson);
  
    console.log("Signup data saved to localStorage");
  
    alert("Signup data saved (simulated)");
  };

    /*
    const { isValid, errors } = validateSignUp(
      signupData.firstName,
      signupData.lastName,
      signupData.email,
      signupData.password
    );
    if (!isValid) {
      setError(Object.values(errors).join(", "));
      return;
    }

    try {
      console.log("Signup successful", signupData);
      // Assuming signup is successful, redirect to the upload page
      navigate("/upload");
    } catch (error) {
      setError("Error during signup. Please try again.");
      console.error(error);
    }
    */
  

  const onChange = (evt) => {
    setSignupData({
      ...signupData,
      [evt.target.id]: evt.target.value,
    });
  };

  const onFileChange = (event) => {
    console.log("File Chosen:", event.target.files[0])
    setSignupData({
      ...signupData,
      licenseImage: event.target.files[0] 
    });
  };


  return (
    <section clas="bg-gray-50 dark:bg-gray-900">
      <div class="flex flex-col items-center justify-center px-6 py-8 mx-auto md:h-screen lg:py-0">
        <div class="w-full bg-white rounded-lg shadow dark:border md:mt-0 sm:max-w-md xl:p-0 dark:bg-gray-800 dark:border-gray-700">
          <div class="p-6 space-y-4 md:space-y-6 sm:p-8">
            <h1 class="text-xl font-bold leading-tight tracking-tight text-gray-900 md:text-2xl dark:text-white">
              Sign up to a drivers account
            </h1>
            <form onChange={onChange}
            
              class="space-y-4 md:space-y-6"
              action="#"
              onSubmit={performSignup}
            >
              <div>
                <label
                  htmlFor="email"
                  class="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
                >
                  Email
                </label>
                <input
                  type="email"
                  name="email"
                  id="email"
                    ref={email}
                  class="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                  placeholder="name@company.com"

                  required=""
                ></input>
              </div>
              <div>
                <label
                  htmlFor="password"
                  class="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
                >
                  Password
                </label>
                <input
                  type="password"
                  name="password"
                  id="password"
                  ref={password}
                  placeholder="••••••••"
                  class="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                  required=""
                ></input>
              </div>
              <div>
                <label
                  htmlFor="address"
                  class="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
                >
                  Address
                </label>
                <input
                  type="text"
                  name="address"
                  id="address"
                    ref={address}
                  class="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                  required=""
                ></input>
              </div>
              <div>
                <label
                  htmlFor="fullname"
                  class="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
                >
                  Full Name
                </label>
                <input
                  type="text"
                  name="fullname"
                  id="fullname"
                  ref={fullname}
                  class="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                  required=""
                ></input>
              </div>
              <div>
                <label htmlFor="licenseImage" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">
                    Upload your driver's license
                </label>
                <input
                    type="file"
                    id="licenseImage"
                    ref={licenseImage}
                    name="licenseImage"
                    accept="image/*" 
                    onChange={onFileChange}
                    className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:text-white"
                    required=""
                />
                </div>
                <div>
                <label htmlFor="licenseImage" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">
                    Upload your Student ID
                </label>
                <input
                    type="file"
                    id="StudentImage"
                    ref={StudentImage}
                    name="StudentImage"
                    accept="image/*" 
                    onChange={onFileChange}
                    className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:text-white"
                    required=""
                />
                </div>
              
              <button 
                type="submit"
                class="w-full text-gray-800 bg-primary-600 hover:bg-primary-700 focus:ring-4 focus:outline-none focus:ring-primary-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-primary-600 dark:hover:bg-primary-700 dark:focus:ring-primary-800"
              >
                Sign up
              </button>
            </form>
          </div>
        </div>
      </div>
    </section>
  );
}



export default Signup;
