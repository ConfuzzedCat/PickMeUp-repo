import React from "react"
import ReactDOM from "react-dom/client"
import App from "./App.jsx"
import "./index.css"
import Routes from "./pages/Routes.jsx";
import SearchForRoutes from "./components/SearchForRoutes.jsx";
import {
  createBrowserRouter,
  createRoutesFromElements,
  RouterProvider,
  Route,
} from "react-router-dom";
import Signup from "./signup";
import Requests from "./components/Requests.jsx";
import CreateRoute from "./pages/createRoute.jsx"


const routes = createBrowserRouter(
    createRoutesFromElements(
        <Route path= "/" element={<App />}>
            <Route path="/available_routes" element={<SearchForRoutes />} />
            <Route path="/routes" element={<Routes />} />
            <Route path="/signup" element={<Signup />} />
            <Route path="/requests" element={<Requests/>} />
            <Route path="/create-route" element={<CreateRoute />} />
        </Route>
    )

)

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <RouterProvider router={routes} />
  </React.StrictMode>
)