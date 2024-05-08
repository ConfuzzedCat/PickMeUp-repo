import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App.jsx";
import SearchForRoutes from "./components/SearchForRoutes.jsx"
import "./index.css";
import { RouterProvider, Route, createRoutesFromElements, createBrowserRouter } from 'react-router-dom'

const routes = createBrowserRouter(
  createRoutesFromElements(
      <Route path= "/" element={<App />}>
          <Route path="/available_routes" element={<SearchForRoutes />} />
      </Route>
  )
)

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <RouterProvider router={routes} />
  </React.StrictMode>,
)