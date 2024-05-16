import React, { useState, useRef } from "react";
import { useNavigate } from "react-router-dom";

function Signup() {
  const init = {
    email: "",
    password: "",
    address: "",
    fullname: "",
    licenseImage: null,
    studentImage: null,
  };
  const [signupData, setSignupData] = useState(init);
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const emailRef = useRef(null);
  const passwordRef = useRef(null);
  const addressRef = useRef(null);
  const fullnameRef = useRef(null);
  const licenseImageRef = useRef(null);
  const studentImageRef = useRef(null);

  const performSignup = (evt) => {
    evt.preventDefault();
  
    // Validation checks
    if (!signupData.email.includes("@")) {
      setError("Please enter a valid email address.");
      emailRef.current.focus();
      return;
    }
  
    if (signupData.password.length < 6) {
      setError("Password must be at least 6 characters.");
      passwordRef.current.focus();
      return;
    }
  
    if (!signupData.address) {
      setError("Please enter your address.");
      addressRef.current.focus();
      return;
    }
  
    if (!signupData.fullname) {
      setError("Please enter your full name.");
      fullnameRef.current.focus();
      return;
    }
  
    
    //console.log("Signup data:", signupData);
    //alert("Signup successful");
   
    // Save signup data to local storage in the browser (to test if it saves correctly)
  localStorage.setItem("signupData", JSON.stringify(signupData));
  console.log("Signup data saved to localStorage");

  alert("Signup successful");

  navigate("/");
};
  
  const onChange = (evt) => {
    setSignupData({
      ...signupData,
      [evt.target.id]: evt.target.value,
    });
  };

  const onFileChange = (event) => {
    const file = event.target.files[0];
    console.log("File Chosen:", file);
  
    const validTypes = ["image/jpeg", "image/png"];
    if (!validTypes.includes(file.type)) {
      setError("Please upload a valid image file (JPEG or PNG).");
      event.target.value = "";
      return;
    }
  
    if (event.target.id === "licenseImage") {
      setSignupData({
        ...signupData,
        licenseImage: file,
      });
    } else if (event.target.id === "studentImage") {
      setSignupData({
        ...signupData,
        studentImage: file,
      });
    }
};

  

  return (
    <section className="bg-gray-50 dark:bg-gray-900">
      <div className="flex flex-col items-center justify-center px-6 py-8 mx-auto md:h-screen lg:py-0">
        <div className="w-full bg-white rounded-lg shadow dark:border md:mt-0 sm:max-w-md xl:p-0 dark:bg-gray-800 dark:border-gray-700">
          <div className="p-6 space-y-4 md:space-y-6 sm:p-8">
            <h1 className="text-xl font-bold leading-tight tracking-tight text-gray-900 md:text-2xl dark:text-white">
              Sign up to a driver's account
            </h1>
            <form
              onChange={onChange}
              className="space-y-4 md:space-y-6"
              onSubmit={performSignup}
            >
              <div>
                <label
                  htmlFor="email"
                  className="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
                >
                  Email
                </label>
                <input
                  type="email"
                  name="email"
                  id="email"
                  ref={emailRef}
                  className="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                  placeholder="name@company.com"
                  required
                />
              </div>
              <div>
                <label
                  htmlFor="password"
                  className="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
                >
                  Password
                </label>
                <input
                  type="password"
                  name="password"
                  id="password"
                  ref={passwordRef}
                  placeholder="••••••••"
                  className="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                  required
                />
              </div>
              <div>
                <label
                  htmlFor="address"
                  className="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
                >
                  Address
                </label>
                <input
                  type="text"
                  name="address"
                  id="address"
                  ref={addressRef}
                  className="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                  required
                />
              </div>
              <div>
                <label
                  htmlFor="fullname"
                  className="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
                >
                  Full Name
                </label>
                <input
                  type="text"
                  name="fullname"
                  id="fullname"
                  ref={fullnameRef}
                  className="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                  required
                />
              </div>
              <div>
                <label
                  htmlFor="licenseImage"
                  className="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
                >
                  Upload your driver's license (.jpg or .png only)
                </label>
                <input
                  type="file"
                  id="licenseImage"
                  ref={licenseImageRef}
                  name="licenseImage"
                  accept="image/*"
                  onChange={onFileChange}
                  className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:text-white"
                  required
                />
              </div>
              <div>
                <label
                  htmlFor="studentImage"
                  className="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
                >
                  Upload your Student ID (.jpg or .png only)
                </label>
                <input
                  type="file"
                  id="studentImage"
                  ref={studentImageRef}
                  name="studentImage"
                  accept="image/*"
                  onChange={onFileChange}
                  className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:text-white"
                  required
                />
              </div>
              {error && (
                <div className="text-red-500 text-sm">{error}</div>
              )}
              <button
                type="submit"
                className="w-full text-gray-800 bg-primary-600 hover:bg-primary-700 focus:ring-4 focus:outline-none focus:ring-primary-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-primary-600 dark:hover:bg-primary-700 dark:focus:ring-primary-800"
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

