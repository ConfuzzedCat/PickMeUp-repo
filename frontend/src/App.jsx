import { useState } from "react";
import {
  BrowserRouter as Router,
  Route,
  NavLink,
  Routes,
} from "react-router-dom";
import "./App.css";
import Rides from "./rides";


function App() {
  const [count, setCount] = useState(0);



  return (
    <Router>
      <header>
        <ul>
          <li>
            <NavLink to="/rides" activeClassName="active">
              Rides
            </NavLink>
          </li>
        </ul>
      </header>

      <Routes>
        <Route path="/rides" element={<Rides />} />
      </Routes>
    </Router>
  );
}

export default App;