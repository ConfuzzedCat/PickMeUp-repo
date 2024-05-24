import React, { useState, useEffect } from "react"
import facade from "../util/apiFacade.js"
import { userFetching } from "../datafetching/UserInfo.js"
import UserInfoComponent from "./UserInfo.jsx"

const Requests = () => {
  const initUser = {
    userID: 1,
    role: "driver",
  }
  const [user, setUser] = useState(initUser)
  const [passengerInfo, setPassengerInfo] = useState([])
  const [chosenRideRequest, setChosenRideRequest] = useState(null)
  const [incomingRequests, setIncomingRequests] = useState([])
  const [outgoingRequests, setOutgoingRequests] = useState([])

  useEffect(() => {
    if (user.role === "driver") {
      var endpoint = "requests/incoming/" + user.userID
      facade.fetchData(endpoint, "GET").then((data) => setIncomingRequests(data))
      endpoint = "requests/outgoing/" + user.userID
      facade.fetchData(endpoint, "GET").then((data) => setOutgoingRequests(data))
    } else {
      const endpoint = "requests/outgoing/" + user.userID
      facade.fetchData(endpoint, "GET").then((data) => setOutgoingRequests(data))
    }
  }, [])

  const handlePassengerInfo = (userID, rideRequestID) => {
    setChosenRideRequest(rideRequestID)
    userFetching(userID, (data) => setPassengerInfo(data))
  }

  return (
    <>
      <div>
        {incomingRequests.length > 0 ? (
          <div>
            <h2>Requests for your rides:</h2>
            <table>
              <thead>
                <tr>
                  <td>Sender of Request:</td>
                  <td>Receiver of Request:</td>
                  <td>Ride:</td>
                  <td></td>
                </tr>
              </thead>
              <tbody>
                {incomingRequests.map((request) => (
                  <React.Fragment key={request.id}>
                    <tr>
                      <td>{request.rideRequestSenderID}</td>
                      <td>{request.rideRequestReceiverID}</td>
                      <td>{request.rideID}</td>
                      <td>
                        <button onClick={() => handlePassengerInfo(request.rideRequestSenderID, request.id)}>See passenger info</button>
                      </td>
                    </tr>
                    {chosenRideRequest === request.id && <tr>{UserInfoComponent(passengerInfo)}</tr>}
                  </React.Fragment>
                ))}
              </tbody>
            </table>
          </div>
        ) : (
          <div>
            <h2>There is no requests for your rides.</h2>
          </div>
        )}
      </div>
      <div>
        {outgoingRequests.length > 0 ? (
          <div>
            <table>
              <thead>
                <tr>
                  <td>Sender of Request:</td>
                  <td>Receiver of Request:</td>
                  <td>Ride:</td>
                </tr>
              </thead>
              <tbody>
                {outgoingRequests.map((request) => (
                  <tr key={request.id}>
                    <td>{request.rideRequestSenderID}</td>
                    <td>{request.rideRequestReceiverID}</td>
                    <td>{request.rideID}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        ) : (
          <div>
            <h2>You have not requested any rides.</h2>
          </div>
        )}
      </div>
    </>
  )
}
export default Requests
