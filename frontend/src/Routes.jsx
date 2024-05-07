import React from "react";
import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import mockData from "./util/routesData.js";

function Routes() {
  const [routes, setRoutes] = useState([]);

  useEffect(() => {
    setRoutes(mockData.getAllRoutes());
  }, []);

  return (
    <div className="overflow-x-auto">
      <h1>Available Routes</h1>

      <select className="select select-ghost w-full max-w-xs">
        <option disabled selected>
          Choose a Filter
        </option>
        <option>Destination</option>
        <option>Handicap Availability</option>
        <option>Time</option>
        <option>Date</option>
        <option>Amount of Passengers</option>
        <option>Car size</option>
        <option>Driver</option>
      </select>

      <button className="btn btn-sm">Apply</button>

      <table className="table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Destination</th>
            <th>Driver</th>
            <th>Time</th>
            <th>Date</th>
            <th>Passengers</th>
            <th>Car Size</th>
            <th>Handicap Accessibility</th>
            <th>Actions</th>  {/* New column header for actions */}
          </tr>
        </thead>
        <tbody>
          {routes.map(route => (
            <tr key={route.id}>
              <td>{route.id}</td>
              <td>{route.destination}</td>
              <td>{route.driver}</td>
              <td>{route.time}</td>
              <td>{route.date}</td>
              <td>{route.passengers}</td>
              <td>{route.carSize}</td>
              <td>{route.handicapAccessibility ? "Yes" : "No"}</td>
              <td>
                <Link to={`/route/${route.id}`} className="btn btn-sm btn-outline">
                  See More
                </Link>
              </td>  {/* Button in a new cell */}
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default Routes;
