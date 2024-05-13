import React, { useState, useEffect } from "react"
import "daisyui/dist/full.css"
import facade from "../util/apiFacade"

function CreateRoute() {
  const [trips, setTrips] = useState([])
  const [formData, setFormData] = useState({
    departureLocation: "",
    destination: "",
    departureDateTime: "",
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
    const { name, value } = e.target
    setFormData({ ...formData, [name]: value })
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    try {
      await facade.createRoute(formData) // Changed from createRecipe to createRoute
      fetchTrips() // Refresh the trips list after creating a new trip
    } catch (error) {
      console.error("Error creating trip:", error)
    }
  }

  return (
    <div
      style={{
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        height: "100vh",
      }}
    >
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
              <label htmlFor="departureLocation">Departure Location:</label>
              <input type="text" id="departureLocation" name="departureLocation" value={formData.departureLocation} onChange={handleChange} required className="input" />
            </div>
            <div className="flex flex-col">
              <label htmlFor="destination">Destination:</label>
              <input type="text" id="destination" name="destination" value={formData.destination} onChange={handleChange} required className="input" />
            </div>
            <div className="flex flex-col">
              <label htmlFor="departureDateTime">Departure Date/Time:</label>
              <input type="datetime-local" id="departureDateTime" name="departureDateTime" value={formData.departureDateTime} onChange={handleChange} required className="input" />
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
                  Departure Location: {trip.departureLocation}, Destination: {trip.destination}, Departure Date/Time: {trip.departureDateTime.toLocaleString()}
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
