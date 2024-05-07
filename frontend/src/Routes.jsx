import React from "react"
import { useState } from "react"
import { useEffect } from "react"

function Routes() {
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
        {/* head */}
        <thead>
          <tr>
            <th></th>
            <th>Route</th>
            <th>Driver</th>
            <th>Time</th>
          </tr>
        </thead>
        <tbody>
          {/* row 1 */}
          <tr className="bg-base-200">
            <th>1</th>
            <td>Lyngby - Nørrebro</td>
            <td>Baby-Driver</td>
            <td>12:30</td>
          </tr>
          {/* row 2 */}
          <tr>
            <th>2</th>
            <td>Nørregårdsvej 26 - lyngby station</td>
            <td>onkel Steward</td>
            <td>9:00</td>
          </tr>
          {/* row 3 */}
          <tr>
            <th>3</th>
            <td>DTU - Cph-business-søerne</td>
            <td>Jens</td>
            <td>20:00</td>
          </tr>
        </tbody>
      </table>
    </div>
  );
}

export default Routes;
