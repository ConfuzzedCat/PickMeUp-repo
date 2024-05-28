import { useState } from "react"
import { BrowserRouter as Router, Route, NavLink, Routes, Outlet } from "react-router-dom"

function App() {
  return (
    <>
      <nav className="bg-gray-800">
        <ul className="flex justify-center">
          <li>
            <NavLink to="/" activeClassName="bg-green-500" className="text-white px-3 py-2 rounded-md text-sm font-medium mx-3">
              Home
            </NavLink>
          </li>
          <li>
            <NavLink to="/signup" activeClassName="bg-green-500" className="text-white px-3 py-2 rounded-md text-sm font-medium mx-3">
              Signup
            </NavLink>
          </li>
          <li>
            <NavLink to="/routes" activeClassName="bg-green-500" className="text-white px-3 py-2 rounded-md text-sm font-medium mx-5">
              Routes
            </NavLink>
          </li>
          <li>
            <NavLink to="/create-route" activeClassName="bg-green-500" className="text-white px-3 py-2 rounded-md text-sm font-medium mx-3">
              Create route
            </NavLink>
          </li>
          <li>
            <NavLink to="/requests" activeClassName="bg-green-500" className="text-white px-3 py-2 rounded-md text-sm font-medium mx-3">
              Requests
            </NavLink>
          </li>
          <li>
            <NavLink to="/available_routes" activeClassName="bg-green-500" className="text-white px-3 py-2 rounded-md text-sm font-medium mx-3">
              Search for Routes
            </NavLink>
          </li>
        </ul>
      </nav>
      <Outlet />
    </>
  )
}

export default App
