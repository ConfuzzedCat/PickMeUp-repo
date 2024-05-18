import React, { useState, useEffect } from "react";
import rideRequestFetching from "../datafetching/rideRequestFetching";

const RequestButton = ({ ride }) => {

  //mock a user
  const user1 = {
    id: 3,
    email : "123@mail.com",
    password : "123",
    firstName : "John",
    lastName : "Doe"
  };
  const [selectedRide, setSelectedRide] = useState(ride);

  useEffect(() => {
    setSelectedRide(ride);
  }, [ride]);

 /* const newRequest = {
    ID: {
      requestSenderID: user1.id,
      rideID: selectedRide.id
    },
    rideRequestSenderID: user1.id,
    rideRequestReceiverID: selectedRide.driverId
  };
  */
  const newRequest = {
    rideRequestSenderID: user1.id,
    rideRequestReceiverID: selectedRide.driverId,
    rideID: selectedRide.id
  };
  


  const handleClick = () => {
    console.log(newRequest);
    rideRequestFetching(newRequest); 
  };

  return (
    <button onClick={handleClick} className="btn btn-primary">
      Request
    </button>
  );
};
export default RequestButton;