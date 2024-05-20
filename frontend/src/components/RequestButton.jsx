import React, { useState, useEffect } from "react";
import rideRequestFetching from "../datafetching/rideRequestFetching";

const RequestButton = ({ ride }) => {

  const closeButtonStyle = {
    padding: 10,
    marginTop: 20,
    cursor: 'pointer'
};

  //mock a user
  const user1 = {
    id: 2,
    email : "123@mail.com",
    password : "123",
    firstName : "John",
    lastName : "Doe"
  };
  const [selectedRide, setSelectedRide] = useState(ride);

  useEffect(() => {
    setSelectedRide(ride);
  }, [ride]);


  const newRequest = {
    rideRequestSenderID: user1.id,
    rideRequestReceiverID: selectedRide.driverId,
    rideID: selectedRide.id
  };

  const rejectRequestDuplicationMessage = () => {
    window.alert("A request already exists for this ride!")
  };
  


  const handleClick = () => {
    console.log(newRequest);
    rideRequestFetching(newRequest, rejectRequestDuplicationMessage); 
  };

  return (
    <button onClick={handleClick} style={closeButtonStyle}>
      Request
    </button>
  );
};
export default RequestButton;