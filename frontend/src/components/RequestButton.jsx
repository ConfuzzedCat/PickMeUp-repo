import React, { useState, useEffect } from "react"
import rideRequestFetching from "../datafetching/rideRequestFetching"
import { redirect, useNavigate } from "react-router-dom"

const RequestButton = ({ ride }) => {
  const closeButtonStyle = {
    padding: 10,
    marginTop: 20,
    cursor: "pointer",
  }

  //mock a user
  //TODO: remove mock and get user from DB
  const user1 = {
    id: 1,
    email: "123@mail.com",
    password: "123",
    firstName: "John",
    lastName: "Doe",
  }
  const [selectedRide, setSelectedRide] = useState(ride)

  useEffect(() => {
    setSelectedRide(ride)
  }, [ride])

  const newRequest = {
    rideRequestSenderID: user1.id,
    rideRequestReceiverID: selectedRide.driverId,
    rideID: selectedRide.id,
  }
  let creation = false

  const rejectRequestDuplicationMessage = () => {
    window.alert("A request already exists for this ride!")
    creation = true
  }
  const navigate = useNavigate()

  const handleClick = () => {
    console.log(newRequest)
    rideRequestFetching(newRequest, rejectRequestDuplicationMessage)
    if (creation === false) {
      navigate("/requests")
    }
  }

  return (
    <button onClick={handleClick} style={closeButtonStyle}>
      Request
    </button>
  )
}
export default RequestButton
