import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import facade from "../util/apiFacade";
import AddReview from "../components/AddReview";

function RouteDetails() {
  const { id } = useParams();
  const [route, setRoute] = useState(null);

  useEffect(() => {
    facade.fetchData(`rides/${id}`, "GET").then((data) => {
      setRoute(data);
    });
  }, [id]);

  if (!route) {
    return <div>Loading...</div>;
  }

  return (
    <div className="container mx-auto prose-xl">
      <h1>Route Details</h1>
      <table className="table">
        <tbody>
          <tr>
            <th>ID</th>
            <td>{route.id}</td>
          </tr>
          <tr>
            <th>Start Location</th>
            <td>{route.startLocation}</td>
          </tr>
          <tr>
            <th>Destination</th>
            <td>{route.endLocation}</td>
          </tr>
          <tr>
            <th>Driver</th>
            <td>{route.driverId}</td>
          </tr>
          <tr>
            <th>Time</th>
            <td>{route.timeInMinutes}</td>
          </tr>
          <tr>
            <th>Date</th>
            <td>{route.departureTime}</td>
          </tr>
          <tr>
            <th>Passengers</th>
            <td>{route.passengerAmount}</td>
          </tr>
          <tr>
            <th>Car Size</th>
            <td>{route.carSize}</td>
          </tr>
          <tr>
            <th>Handicap Accessibility</th>
            <td>{route.handicapAvailability ? "Yes" : "No"}</td>
          </tr>
        </tbody>
      </table>
      <AddReview route={route} />
    </div>
  );
}

export default RouteDetails;
