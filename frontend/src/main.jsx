import React from "react"
import ReactDOM from "react-dom/client"
import App from "./App.jsx"
import "./index.css"
import Routes from "./pages/Routes.jsx";
import Routes from "./rides.jsx";
import SearchForRoutes from "./components/SearchForRoutes.jsx";
import { RouterProvider, Route, createRoutesFromElements, createBrowserRouter } from "react-router-dom"

const routes = createBrowserRouter(
  createRoutesFromElements(
    <Route path="/" element={<App />}>
      <Route path="/routes" element={<Routes />} />
      <Route path="/rides" element={<Rides />} />
      <Route path="/available_routes" element={<SearchForRoutes />} />
    </Route>
  )
)

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <RouterProvider router={routes} />
  </React.StrictMode>
)