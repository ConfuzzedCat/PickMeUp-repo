import React from "react"
import { useState, useEffect } from "react"
import { Link } from "react-router-dom"
import mockData from "../util/routesData.js"
import RoutesFilter from "../components/RoutesFilter.jsx"
import facade from "../util/apiFacade.js"

function Routes() {
  const [routes, setRoutes] = useState([])

  useEffect(() => {
    facade.fetchData("rides/", "GET").then((data) => {
      console.log(data)
      setRoutes(data)
    })
  }, [])

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
            <th>Actions</th> {/* New column header for actions */}
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
                <Link to={`/route/${route.id}`} className="btn btn-sm btn-outline">
                  See More
                </Link>
              </td>{" "}
              {/* Button in a new cell */}
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  )
}

export default Routes
