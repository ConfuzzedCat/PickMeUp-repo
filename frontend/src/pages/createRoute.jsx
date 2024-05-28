import React, { useState, useEffect } from "react"
import "daisyui/dist/full.css"
import facade from "../util/apiFacade"

function CreateRoute() {
  const [trips, setTrips] = useState([])
  const [formData, setFormData] = useState({
    driverId: 1, // hardcoded driverId for now
    startPostalCode: "",
    endPostalCode: "",
    startLocation: "",
    endLocation: "",
    routeLength: "",
    timeInMinutes: "",
    handicapAvailability: false,
    passengerAmount: "",
    carSize: "",
    departureTime: "", // Updated to accept date and time in "yyyy-MM-dd HH:mm" format
  })

  useEffect(() => {
    fetchTrips()
  }, [])

  const fetchTrips = async () => {
    try {
      const data = await facade.fetchData("rides", "GET")
      setTrips(data)
    } catch (error) {
      console.error("Error fetching trips:", error)
    }
  }

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target
    const val = type === "checkbox" ? checked : value
    setFormData({ ...formData, [name]: val })
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    try {
      // Ensure the departureDateTime is formatted correctly before sending
      const formattedDateTime = formatDateTime(formData.departureTime)
      await facade.createRoute({ ...formData, departureTime: formattedDateTime })
      fetchTrips()
    } catch (error) {
      console.error("Error creating trip:", error)
    }
  }

  // Function to format date and time to "yyyy-MM-dd HH:mm" format
  const formatDateTime = (dateTime) => {
    const date = new Date(dateTime)
    const formattedDateTime = date.toISOString().slice(0, 16).replace("T", " ")
    return formattedDateTime
  }

  return (
    <div className="container mx-auto prose-xl">
      <div className="flex flex-col items-center">
        <h1 style={{ fontWeight: "bold" }} className="mb-8 text-4xl text-center">
          Driver
        </h1>
        <div className="card p-6 mb-4 w-96">
          <h2 style={{ fontWeight: "bold" }} className="mb-4 text-center">
            Create Trip
          </h2>
          <form id="tripForm" className="flex flex-col items-center gap-4" onSubmit={handleSubmit}>
            <div className="flex flex-col">
              <label htmlFor="startPostalCode">Start Postal Code:</label>
              <input type="text" id="startPostalCode" name="startPostalCode" value={formData.startPostalCode} onChange={handleChange} required className="input input-bordered" />
            </div>
            <div className="flex flex-col">
              <label htmlFor="endPostalCode">End Postal Code:</label>
              <input type="text" id="endPostalCode" name="endPostalCode" value={formData.endPostalCode} onChange={handleChange} required className="input input-bordered" />
            </div>
            <div className="flex flex-col">
              <label htmlFor="startLocation">Start Location:</label>
              <input type="text" id="startLocation" name="startLocation" value={formData.startLocation} onChange={handleChange} required className="input input-bordered" />
            </div>
            <div className="flex flex-col">
              <label htmlFor="endLocation">End Location:</label>
              <input type="text" id="endLocation" name="endLocation" value={formData.endLocation} onChange={handleChange} required className="input input-bordered" />
            </div>
            <div className="flex flex-col">
              <label htmlFor="routeLength">Route Length:</label>
              <input type="number" id="routeLength" name="routeLength" value={formData.routeLength} onChange={handleChange} required className="input input-bordered" />
            </div>
            <div className="flex flex-col">
              <label htmlFor="timeInMinutes">Time In Minutes:</label>
              <input type="number" id="timeInMinutes" name="timeInMinutes" value={formData.timeInMinutes} onChange={handleChange} required className="input input-bordered" />
            </div>
            <div className="flex flex-col">
              <label htmlFor="handicapAvailability">Handicap Availability:</label>
              <input type="checkbox" id="handicapAvailability" name="handicapAvailability" checked={formData.handicapAvailability} onChange={handleChange} className="radio" />
            </div>
            <div className="flex flex-col">
              <label htmlFor="passengerAmount">Passenger Amount:</label>
              <input type="number" id="passengerAmount" name="passengerAmount" value={formData.passengerAmount} onChange={handleChange} required className="input input-bordered" />
            </div>
            <div className="flex flex-col">
              <label htmlFor="carSize">Car Size:</label>
              <input type="number" id="carSize" name="carSize" value={formData.carSize} onChange={handleChange} required className="input input-bordered" />
            </div>
            <div className="flex flex-col">
              <label htmlFor="departureTime">Departure Date/Time:</label>
              <input type="datetime-local" id="departureTime" name="departureTime" value={formData.departureTime} onChange={handleChange} required className="input input-bordered" />
            </div>
            <button type="submit" className="btn btn-primary w-full text-center">
              Create Trip
            </button>
          </form>
        </div>
        <div className="card p-6 w-96">
          <h2 className="mb-4 font-bold">Trips</h2>
          <ul id="tripList" className="flex flex-col gap-2">
            {trips.length > 0 ? (
              trips.map((trip) => (
                <li key={trip.id}>
                  Start Location: {trip.startLocation}, End Location: {trip.endLocation}, Route Length: {trip.routeLength}, Time In Minutes: {trip.timeInMinutes}, Handicap Availability: {trip.handicapAvailability ? "Yes" : "No"}, Passenger Amount: {trip.passengerAmount}, Car Size: {trip.carSize}, Departure Date/Time: {new Date(trip.departureDateTime).toLocaleString()}
                </li>
              ))
            ) : (
              <li>No trips available</li>
            )}
          </ul>
        </div>
      </div>
    </div>
  )
}

export default CreateRoute
