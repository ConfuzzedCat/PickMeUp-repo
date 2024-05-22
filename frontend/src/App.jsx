import { useState } from "react";
import {
  BrowserRouter as Router,
  Route,
  NavLink,
  Routes,
  Outlet,
} from "react-router-dom";
import "./App.css";

function App() {
  return (

    <>
     <div>
        <ul>
          <li>
            <NavLink to="/signup" activeClassName="active"> Signup</NavLink>
            <NavLink to="/routes" activeClassName="active"> Routes</NavLink>
            <NavLink to="/requests" activeClassName="active"> Requests</NavLink>
            <NavLink to="/available_routes" activeClassName="active"> Search for Routes</NavLink>


          </li>
        </ul>
        
      </div>

  <Outlet>
    </Outlet>

</>
  );
}

export default App
