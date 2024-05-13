import React from "react";
import {
    BrowserRouter as Router,
    Routes,
    Route,
    Navigate,
} from "react-router-dom";
import { Outlet } from "react-router-dom"
import CreateRoute from "./pages/createRoute";

function App() {
    return (
        <Router>

            <div>
                <Routes>
                    <Route path="/" element={<Navigate to="/create-route" />} />
                    <Route path="/create-route" element={<CreateRoute />} />
                </Routes>
            </div>
        </Router>
    <Outlet />
    );
}

export default App;
