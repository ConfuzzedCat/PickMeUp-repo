import { useState } from "react";
import {
  BrowserRouter as Router,
  Route,
  NavLink,
  Routes,
} from "react-router-dom";
import "./App.css";
import Signup from "./signup";

function App() {
  const [count, setCount] = useState(0);

  return (
    <Router>
      <header>
        <ul>
          <li>
            <NavLink to="/signup" activeClassName="active">
              Signup
            </NavLink>
          </li>
        </ul>
      </header>

      <Routes>
        <Route path="/signup" element={<Signup />} />
      </Routes>
    </Router>
  );
}

export default App;
