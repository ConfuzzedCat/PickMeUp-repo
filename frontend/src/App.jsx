import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";

function App() {
  return (
    <Router>
      <div>
        <Routes>
          <Route path="/create-routue" element={<createRoute />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
