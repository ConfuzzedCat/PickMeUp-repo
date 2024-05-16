import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import facade from "../util/apiFacade.js";
import RoutesFilter from "../components/RoutesFilter.jsx";

// Sorry, det her var den letteste måde at gøre det på
function RideModal({ ride, onClose }) {
    if (!ride) return null;

    const modalStyle = {
        position: "fixed",
        top: 0,
        left: 0,
        right: 0,
        bottom: 0,
        backgroundColor: "rgba(0,0,0,0)",
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        zIndex: 1000
    };

    const modalContentStyle = {
        backgroundColor: "black",
        padding: 20,
        borderRadius: 5,
        width: "80%",
        maxWidth: 500
    };

    const closeButtonStyle = {
        padding: 10,
        marginTop: 20,
        cursor: 'pointer'
    };

    return (
        <div style={modalStyle}>
            <div style={modalContentStyle}>
                <h2>Ride Details</h2>
                <p><strong>Start Location:</strong> {ride.startLocation}</p>
                <p><strong>End Location:</strong> {ride.endLocation}</p>
                <p><strong>Driver ID:</strong> {ride.driverId}</p>
                <p><strong>Route Length:</strong> {ride.routeLength} km</p>
                <p><strong>Time in Minutes:</strong> {ride.timeInMinutes} minutes</p>
                <p><strong>Departure Time:</strong> {ride.departureTime}</p>
                <button onClick={onClose} style={closeButtonStyle}>Close</button>
            </div>
        </div>
    );
}

function Routes() {
  const [routes, setRoutes] = useState([]);
  const [selectedRide, setSelectedRide] = useState(null); 

  useEffect(() => {
    facade.fetchData("rides/", "GET").then((data) => {
      setRoutes(data);
    });
  }, []);

  const handleShowDetails = (route) => {
    setSelectedRide(route);
  };

  return (
    <div className="container mx-auto prose-xl">
      <h1>Available Routes</h1>
      <RoutesFilter setRoutes={setRoutes} />
      <table className="table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Start location</th>
            <th>Destination</th>
            <th>Driver</th>
            <th>Time</th>
            <th>Date</th>
            <th>Passengers</th>
            <th>Car seats</th>
            <th>Handicap Accessibility</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {routes.map((route) => (
            <tr key={route.id}>
              <td>{route.id}</td>
              <td>{route.startLocation}</td>
              <td>{route.endLocation}</td>
              <td>{route.driverId}</td>
              <td>{route.timeInMinutes}</td>
              <td>{route.departureTime}</td>
              <td>{route.passengerAmount}</td>
              <td>{route.carSize}</td>
              <td>{route.handicapAvailability ? "Yes" : "No"}</td>
              <td>
                <button onClick={() => handleShowDetails(route)} className="btn btn-sm btn-outline">
                  See More
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      {selectedRide && <RideModal ride={selectedRide} onClose={() => setSelectedRide(null)} />}
    </div>
  );
}

export default Routes;
