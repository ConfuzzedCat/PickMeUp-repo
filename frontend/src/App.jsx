import { useState } from "react";
import {
  BrowserRouter as Router,
  Route,
  NavLink,
  Routes,
  Outlet,
} from "react-router-dom";

function App() {
  return (
    <>
      <nav className="bg-gray-800">
        <ul className="flex">
          <li>
            <NavLink
              to="/signup"
              activeClassName="bg-green-500"
              className="text-white px-3 py-2 rounded-md text-sm font-medium"
            >
              Signup
            </NavLink>
          </li>
          <li>
            <NavLink
              to="/routes"
              activeClassName="bg-green-500"
              className="text-white px-3 py-2 rounded-md text-sm font-medium"
            >
              Routes
            </NavLink>
          </li>
          <li>
            <NavLink
              to="/requests"
              activeClassName="bg-green-500"
              className="text-white px-3 py-2 rounded-md text-sm font-medium"
            >
              Requests
            </NavLink>
          </li>
          <li>
            <NavLink
              to="/available_routes"
              activeClassName="bg-green-500"
              className="text-white px-3 py-2 rounded-md text-sm font-medium"
            >
              Search for Routes
            </NavLink>
          </li>
        </ul>
      </nav>
      <Outlet />
    </>
  );
}

export default App;
