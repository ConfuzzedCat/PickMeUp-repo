import React, { useState } from "react"

function RoutesFilter({ setRoutes }) {
  const [formData, setFormData] = useState({
    destination: "",
    passenger: "",
    driver: "",
    handicapAcc: "",
    carSize: "",
    dateTime: "",
  })

  const handleChange = (e) => {
    const { name, value } = e.target
    if (name === "passenger" && (isNaN(value) || value < 0)) return
    setFormData({ ...formData, [name]: value })
  }

  const handleSubmit = (e) => {
    e.preventDefault()
    // Here you can use formData to perform your search
    console.log(formData)
    // Clear the form fields after submission if needed
    setFormData({
      destination: "",
      passenger: "",
      driver: "",
      handicapAcc: "",
      carSize: "",
      dateTime: "",
    })
  }

  return (
    <form onSubmit={handleSubmit} className="flex flex-row flex-wrap space-x-2">
      <label className="form-control">
        <div className="label">
          <span className="label-text">Destination</span>
        </div>
        <input type="text" name="destination" value={formData.destination} onChange={handleChange} className="input input-sm input-bordered" />
      </label>
      <label className="form-control">
        <div className="label">
          <span className="label-text">Passengers participating</span>
        </div>
        <input type="number" name="passenger" onKeyDown={(evt) => evt.key === "e" && evt.preventDefault()} value={formData.passenger} onChange={handleChange} className="input input-sm input-bordered" />
      </label>

      <label className="form-control">
        <div className="label">
          <span className="label-text">Driver</span>
        </div>
        <input type="text" name="driver" value={formData.driver} onChange={handleChange} className="input input-sm input-bordered" />
      </label>

      <label className="form-control">
        <div className="label">
          <span className="label-text"> Car size</span>
        </div>
        <select name="carSize" value={formData.carSize} onChange={handleChange} className="select select-bordered select-sm w-full">
          <option value="">Select</option>
          <option value="small">Small</option>
          <option value="medium">Medium</option>
          <option value="large">Large</option>
        </select>{" "}
      </label>

      <label className="form-control">
        <div className="label">
          <span className="label-text"> Handicap accessibility</span>
        </div>
        <select name="handicapAcc" value={formData.handicapAcc} onChange={handleChange} className="select select-bordered select-sm w-full">
          <option value="">Select</option>
          <option value="true">Yes</option>
          <option value="false">No</option>
        </select>
      </label>

      <label className="form-control">
        <div className="label">
          <span className="label-text"> Date and time</span>
        </div>
        <input type="datetime-local" name="dateTime" value={formData.dateTime} onChange={handleChange} className="input input-sm input-bordered" />
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
