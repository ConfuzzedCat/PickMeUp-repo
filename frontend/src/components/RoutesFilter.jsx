import React, { useState } from "react"
import facade from "../util/apiFacade.js"

function RoutesFilter({ setRoutes }) {
  const [formData, setFormData] = useState({
    endLocation: "",
    passengerAmount: "",
    driver: "",
    handicapAvailability: null,
    carSize: "",
    start: "",
    end: "",
  })

  const handleChange = (e) => {
    const { name, value } = e.target

    if (name === "passengerAmount" && (isNaN(value) || value < 0)) return

    let newValue = value
    if (name === "handicapAvailability" && value !== "") {
      newValue = value === "true"
    }

    // if carSize convert to number
    if (name === "carSize" && value !== "") {
      newValue = parseInt(value)
    }

    setFormData({ ...formData, [name]: newValue })
  }

  const handleSubmit = (e) => {
    e.preventDefault()

    const searchParams = Object.fromEntries(Object.entries(formData).filter(([_, v]) => v !== "" && v !== null))
    console.log(searchParams)
    facade.fetchData("rides/search", "POST", searchParams).then((data) => {
      setRoutes(data)
    })
  }

  return (
    <form onSubmit={handleSubmit} className="flex flex-row flex-wrap space-x-2">
      <label className="form-control">
        <div className="label">
          <span className="label-text">Destination</span>
        </div>
        <input type="text" name="endLocation" value={formData.endLocation} onChange={handleChange} className="input input-sm input-bordered" />
      </label>
      <label className="form-control">
        <div className="label">
          <span className="label-text">Passengers participating</span>
        </div>
        <input type="number" name="passengerAmount" onKeyDown={(evt) => evt.key === "e" && evt.preventDefault()} value={formData.passengerAmount} onChange={handleChange} className="input input-sm input-bordered" />
      </label>

      <label className="form-control">
        <div className="label">
          <span className="label-text">Driver</span>
        </div>
        <input type="text" name="driver" value={formData.driver} onChange={handleChange} className="input input-sm input-bordered" />
      </label>

      <label className="form-control">
        <div className="label">
          <span className="label-text">Car seats</span>
        </div>
        <select name="carSize" value={formData.carSize} onChange={handleChange} className="select select-bordered select-sm w-full">
          <option value="">Select</option>
          <option value="1">1</option>
          <option value="2">2</option>
          <option value="3">3</option>
          <option value="4">4</option>
          <option value="5">5</option>
        </select>{" "}
      </label>

      <label className="form-control">
        <div className="label">
          <span className="label-text"> Handicap accessibility</span>
        </div>
        <select name="handicapAvailability" value={formData.handicapAvailability} onChange={handleChange} className="select select-bordered select-sm w-full">
          <option value="">Select</option>
          <option value="true">Yes</option>
          <option value="false">No</option>
        </select>
      </label>

      <label className="form-control">
        <div className="label">
          <span className="label-text">From</span>
        </div>
        <input type="datetime-local" name="start" value={formData.start} onChange={handleChange} className="input input-sm input-bordered" />
      </label>

      <label className="form-control">
        <div className="label">
          <span className="label-text">To</span>
        </div>
        <input type="datetime-local" name="end" value={formData.end} onChange={handleChange} className="input input-sm input-bordered" />
      </label>

      <div className="self-end">
        <button type="submit" className="btn btn-sm w-full">
          Search
        </button>
      </div>
    </form>
  )
}

export default RoutesFilter
