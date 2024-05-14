import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App.jsx";
import "./index.css";
import { RouterProvider, Route, createRoutesFromElements, createBrowserRouter } from 'react-router-dom'
import Routes from "./pages/Routes.jsx"
import SearchForRoutes from "./components/SearchForRoutes.jsx";
import Signup from "./signup";

const routes = createBrowserRouter(
    createRoutesFromElements(
        <Route path= "/" element={<App />}>
            <Route path="/available_routes" element={<SearchForRoutes />} />
            <Route path="/routes" element={<Routes />} />
            <Route path="/signup" element={<Signup />} />
        </Route>
    )
)

ReactDOM.createRoot(document.getElementById('root')).render(
    <React.StrictMode>
        <RouterProvider router={routes} />
    </React.StrictMode>,
)