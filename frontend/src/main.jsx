import React from "react"
import ReactDOM from "react-dom/client"
import App from "./App.jsx"
import "./index.css"
import Routes from "./pages/Routes.jsx";
import Rides from "./rides.jsx";
import SearchForRoutes from "./components/SearchForRoutes.jsx";
import RouteDetails from "./components/RouteDetails.jsx";
import {
  createBrowserRouter,
  createRoutesFromElements,
  RouterProvider,
  Route,
} from "react-router-dom";
import Signup from "./signup";

const routes = createBrowserRouter(
    createRoutesFromElements(
        <Route path= "/" element={<App />}>
            <Route path="/available_routes" element={<SearchForRoutes />} />
            <Route path="/routes" element={<Routes />} />
            <Route path="/signup" element={<Signup />} />
            <Route path="/rides" element={<Rides />} />
            <Route path="/route/:id" element={<RouteDetails />} />
        </Route>
    )

)

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <RouterProvider router={routes} />
  </React.StrictMode>
)