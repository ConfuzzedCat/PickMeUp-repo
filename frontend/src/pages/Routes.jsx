import React, { useState, useEffect } from "react";
import facade from "../util/apiFacade.js";
import RoutesFilter from "../components/RoutesFilter.jsx";
import RideModal from "../components/RideModal.jsx";
import RatingReviewModal from "../components/RatingReviewModal.jsx";

function Routes() {
  const [routes, setRoutes] = useState([]);
  const [selectedRide, setSelectedRide] = useState(null);
  const [selectedReview, setSelectedReview] = useState(null);

  useEffect(() => {
    facade.fetchData("rides/", "GET").then((data) => {
      setRoutes(data);
    });
  }, []);

  const handleShowRideDetails = (route) => {
    setSelectedRide(route);
  };

  const handleShowReviewDetails = (driver) => {
    setSelectedReview(driver);
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
            <th>🌟</th>
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
              <td><p onClick={() => handleShowReviewDetails(driver)}>🌟🌟🌟🌟🌟</p></td>
              <td>{route.timeInMinutes}</td>
              <td>{route.departureTime}</td>
              <td>{route.passengerAmount}</td>
              <td>{route.carSize}</td>
              <td>{route.handicapAvailability ? "Yes" : "No"}</td>
              <td>
                <button onClick={() => handleShowRideDetails(route)} className="btn btn-sm btn-outline">
                  Ride Details
                </button>
                <button onClick={() => handleShowReviewDetails(route.driverId)} className="btn btn-sm btn-outline">
                  Review
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      {selectedRide && <RideModal ride={selectedRide} onClose={() => setSelectedRide(null)} />}
      {selectedReview && <RatingReviewModal driver={selectedReview} onClose={() => setSelectedReview(null)} />}
    </div>
  );
}

export default Routes;
