<<<<<<< HEAD
import React from "react"
import ReactDOM from "react-dom/client"
import App from "./App.jsx"
import "./index.css"
import Routes from "./pages/Routes.jsx";
import SearchForRoutes from "./components/SearchForRoutes.jsx";
import { RouterProvider, Route, createRoutesFromElements, createBrowserRouter } from "react-router-dom"

const routes = createBrowserRouter(
  createRoutesFromElements(
    <Route path="/" element={<App />}>
      <Route path="/routes" element={<Routes />} />
      <Route path="/available_routes" element={<SearchForRoutes />} />
    </Route>
  )
)

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <RouterProvider router={routes} />
  </React.StrictMode>
)
=======
import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App.jsx";
import "./index.css";
import { RouterProvider, Route, createRoutesFromElements, createBrowserRouter } from 'react-router-dom'
import Routes from "./pages/Routes.jsx"
import SearchForRoutes from "./components/SearchForRoutes.jsx";

const routes = createBrowserRouter(
    createRoutesFromElements(
        <Route path= "/" element={<App />}>
            <Route path="/available_routes" element={<SearchForRoutes />} />
            <Route path="/routes" element={<Routes />} />
        </Route>
    )
)

ReactDOM.createRoot(document.getElementById('root')).render(
    <React.StrictMode>
        <RouterProvider router={routes} />
    </React.StrictMode>,
)
>>>>>>> cfb957b38200def3b5c649896a501c6f7f6bc24c
