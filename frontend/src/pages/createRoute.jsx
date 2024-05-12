import { useState, useEffect } from "react";
import React from "react";

function createRoute() {
  const [trips, setTrips] = useState([]);

  // Function to fetch trips from backend
  const fetchTrips = async () => {
    try {
      const response = await fetch("/api/v1/routes");
      if (!response.ok) {
        throw new Error("Failed to fetch trips");
      }
      const data = await response.json();
      setTrips(data);
    } catch (error) {
      console.error("Error fetching trips:", error);
    }
  };

  useEffect(() => {
    fetchTrips();
  }, []);

  return (
    <>
      <h1>Driver</h1>
      <div className="card">
        <h2>Create Trip</h2>
        <form id="tripForm">
          <label htmlFor="departureLocation">Departure Location:</label>
          <br />
          <input
            type="text"
            id="departureLocation"
            name="departureLocation"
            required
          />
          <br />
          <br />

          <label htmlFor="destination">Destination:</label>
          <br />
          <input type="text" id="destination" name="destination" required />
          <br />
          <br />

          <label htmlFor="departureDateTime">Departure Date/Time:</label>
          <br />
          <input
            type="datetime-local"
            id="departureDateTime"
            name="departureDateTime"
            required
          />
          <br />
          <br />

          <button type="submit">Create Trip</button>
        </form>
      </div>
      <div className="card">
        <h2>Trips</h2>
        <ul id="tripList">
          {trips.map((trip) => (
            <li key={trip.id}>
              Departure Location: {trip.departureLocation}, Destination:{" "}
              {trip.destination}, Departure Date/Time: {trip.departureDateTime}
            </li>
          ))}
        </ul>
      </div>
    </>
  );
}

export default createRoute;
